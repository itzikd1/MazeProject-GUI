package ViewModel;

import Model.IModel;
import View.MazeDisplay;

public class MyViewModel {
    private IModel mode;
    public MazeDisplay mazeDisplayer;

    public MyViewModel(IModel mode) {
        this.mode = mode;
    }


    public void generateMaze(int width, int height) {
        mode.generateMaze(width,height);
    }


    /**
     * check if string is an int
     */
    private int checkInt(String check) {
        try {
            int temp = Integer.parseInt(check); //use your variable or object in place of obj
            return temp;
        }
        catch (NumberFormatException e) {
            return 10;
        }
    }
}
