package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
responsible for all the function part
 */

public class MyModel extends Observable implements IModel {
    private int[][] maze;
    private Maze Original;
    private boolean solved;
    private boolean gameFinish;
    private int characterPositionRow;
    private int characterPositionColumn;
    private Position endPosition;
    private int[][] mazeSolutionArr;
    private Server serverMazeGenerator;
    private Server serverSolveMaze;
    private ExecutorService threadPool = Executors.newCachedThreadPool();

    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    public void setCharacterPositionRow(int row) {
        this.characterPositionRow = row;
    }


    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    @Override
    public boolean gameFinish() {
        return gameFinish;
    }

    private void MazeToArr(Maze m) {
        int row = m.numOfRows();
        int col = m.numOfColumns();
        maze = new int[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                maze[i][j] = m.getCellValue(i, j);
    }

    @Override
    public int[][] generateMaze(int width, int height) {
        serverMazeGenerator = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        serverMazeGenerator.start();
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        solved = false;
                        gameFinish = false;
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{width, height};
                        toServer.writeObject(mazeDimensions); //send mazedimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed withMyCompressor)from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[mazeDimensions[0] * mazeDimensions[1] + 8 /*CHANGESIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressedmaze -
                        is.read(decompressedMaze); //Fill decompressedMazewith bytes
                        Maze maze = new Maze(decompressedMaze);
                        Position UpdatePos = new Position(1, 1);
                        UpdatePos = maze.getStartPosition();
                        Original = maze;
                        characterPositionColumn = UpdatePos.getColumnIndex();
                        characterPositionRow = UpdatePos.getRowIndex();
                        endPosition = maze.getGoalPosition();
                        MazeToArr(maze);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        serverMazeGenerator.stop();
        setChanged();
        notifyObservers();
        return maze;
    }

    private boolean isNotLegalMove(int x, int y) {
        if (x < 0 || y < 0 || x > maze.length - 1 || y > maze[0].length - 1)
            return true;
        return maze[x][y] == 1;
    }

    @Override
    public void moveCharacter(KeyCode movement) {
        int x = characterPositionRow;
        int y = characterPositionColumn;
        switch (movement) {
            case NUMPAD8:
            case W:
                if (!isNotLegalMove(x - 1, y))
                    characterPositionRow--;
                break;
            case NUMPAD2:
            case S:
                if (!isNotLegalMove(x + 1, y))
                    characterPositionRow++;
                break;
            case NUMPAD6:
            case D:
                if (!isNotLegalMove(x, y + 1))
                    characterPositionColumn++;
                break;
            case A:
            case NUMPAD4:
                if (!isNotLegalMove(x, y - 1))
                    characterPositionColumn--;
                break;
            case NUMPAD3:
                if (!isNotLegalMove(x + 1, y + 1))
                    if (!isNotLegalMove(x, y + 1) || !isNotLegalMove(x + 1, y)) {
                        characterPositionColumn++;
                        characterPositionRow++;
                    }
                break;
            case NUMPAD1:
                if (!isNotLegalMove(x + 1, y - 1))
                    if (!isNotLegalMove(x, y - 1) || !isNotLegalMove(x + 1, y)) {
                        characterPositionColumn--;
                        characterPositionRow++;
                    }
                break;
            case NUMPAD9:
                if (!isNotLegalMove(x - 1, y + 1))
                    if (!isNotLegalMove(x - 1, y) || !isNotLegalMove(x, y + 1)) {
                        characterPositionColumn++;
                        characterPositionRow--;
                    }
                break;
            case NUMPAD7:
                if (!isNotLegalMove(x - 1, y - 1))
                    if (!isNotLegalMove(x, y - 1) || !isNotLegalMove(x - 1, y)) {
                        characterPositionColumn--;
                        characterPositionRow--;
                    }
                break;
        }
        if (endPosition.getColumnIndex() == getCharacterPositionColumn() && endPosition.getRowIndex() == getCharacterPositionRow())
            gameFinish = true;
        setChanged();
        notifyObservers();

    }

    @Override
    public int[][] getMaze() {
        return maze;
    }

    @Override
    public void setGoalPosition(Position goalPosition) {
        this.endPosition = goalPosition;
    }

    public Maze getOriginal() {
        return Original;
    }

    @Override
    public void setMaze(int[][] maze) {
        this.maze = maze;
    }

    public void setMazeOriginal(Maze m){
        this.Original=m;
        MazeToArr(m);
    }

    public boolean isSolved() {
        return this.solved;
    }

    @Override
    public void generateSolution(MyViewModel m, int charRow, int charCol) {
        serverSolveMaze = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        serverSolveMaze.start();
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        Maze maze = Original;
                        maze.setStartPosition(new Position(charRow,charCol));
                        toServer.writeObject(maze); //send maze to server
                        toServer.flush();
                        Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor)from server
                        //Print Maze Solution retrieved from the server
                        solved = true;
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                        int sizeOfSolution = mazeSolutionSteps.size();
                        mazeSolutionArr = new int[2][sizeOfSolution];
                        for (int i = 0; i < mazeSolutionSteps.size(); i++) {
                            mazeSolutionArr[0][i] = ((MazeState) (mazeSolutionSteps.get(i))).getRow();
                            mazeSolutionArr[1][i] = ((MazeState) (mazeSolutionSteps.get(i))).getCol();
                        }
                        setChanged();
                        notifyObservers();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        serverSolveMaze.stop();
    }

    public int[][] getMazeSolutionArr() {
        return mazeSolutionArr;
    }

    public void setCharacterPositionCol(int col) {
        this.characterPositionColumn = col;
    }

    public void save(File file)
    {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fileOutputStream);
            //myMaze.setM_startPosition(new Position(characterRow, characterColumn));
            os.writeObject(Original);
            os.flush();
            os.close();
        } catch (IOException ex) {

        }
    }

    public void load(File file)
    {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream os = new ObjectInputStream(fileInputStream);
            Maze temp = (Maze)os.readObject();
            setMazeOriginal(temp);
            os.close();
            setChanged();
            notifyObservers();
        } catch (IOException ex) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
