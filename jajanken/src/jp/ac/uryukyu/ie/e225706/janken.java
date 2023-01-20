package jp.ac.uryukyu.ie.e225706;

import java.util.*;

abstract class Character{
    private String name;
    private int win;
    private int lose;

    private ArrayList<Action> actions = new ArrayList<>();

    Character(String name, int win, int lose){
        this.name = name;
        this.win = win;
        this.lose = lose;
    }

    void addAct(Action actions) {
        actions.add(action);
    }

    void openStatus(){
        System.out.printf("%s:win %d  lose %d\n", name, win, lose);
    }

    String getName(){
        return this.name;
    }
    int getWin(){
        return this.win;
    }
    int getLose(){
        return this.lose;
    }

    ArrayList<Action> getActions(){
        return actions;
    }

    void setWin(int win){
        this.win = win;
    }
    void setLose(int lose){
        this.lose = lose;
    }
}

class Player extends Character{

    Player(String name, int win, int lose){
        super(name, win, lose);
    }

    @Override
    void act(ArrayList<Character> targets) {
        var command_selector = new CommandSelector();
        
       //選択肢を用意する
        for(var action: getActions()) {
            command_selector.addCommand(action.name());
        }
       //ユーザの選択を待つ
        var command_number = command_selector.waitForUsersCommand("コマンド？");

       //ターゲットも同様に入力
        command_selector.clearCommands();
        for(var target: targets) {
            command_selector.addCommand(target.getName());
        }
    }
}