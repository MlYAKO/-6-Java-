package jp.ac.uryukyu.ie.e225706;

import java.util.*;

abstract class Character{
    private String name;
    private int win;
    private int lose;

    private ArrayList<Hand> hands = new ArrayList<>();

    Character(String name, int win, int lose){
        this.name = name;
        this.win = win;
        this.lose = lose;
    }
    
    void addAction(Hand hand) {
        hands.add(hand);
    }

    void openStatus(){
        System.out.printf("%s:win %d  lose %d\n", name, win, lose);
    }

    abstract void act(ArrayList<Character> targets);

    String getName(){
        return this.name;
    }
    int getWin(){
        return this.win;
    }
    int getLose(){
        return this.lose;
    }

    ArrayList<Hand> getHands(){
        return hands;
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
        for(var action: getHands()) {
            command_selector.addCommand(action.name());
        }
       //ユーザの選択を待つ
        var command_number = command_selector.waitForUsersCommand("最初はグー、ジャンケン...");
        System.out.println("ポンッ！");
        getHands().get(command_number);
    }
}

class GameMaster {
    ArrayList<Character> order = new ArrayList<>();
 
    GameMaster() {
        var denchu = new Player("デンチウ", 0,0);
        denchu.addAction(new Attack());
        //インスタンスのパラメータを変えることで攻撃魔法のバリエーションを作る
        denchu.addAction(new AttackMagic("ジューデン", 30, 10));
        denchu.addAction(new AttackMagic("ギガジューデン", 60, 20));
        denchu.addAction(new AttackMagic("サンダー", 10, 5));
        denchu.addAction(new HealingMagic("デンチ", 20, 10));
 
        var dan = new Enemy("ダンさん", 0,0);
        dan.addAction(new Attack());
        dan.addAction(new AttackMagic("ジューデン", 30, 10));
        dan.addAction(new AttackMagic("ギガジューデン", 60, 20));
        dan.addAction(new AttackMagic("サンダー", 10, 5));
        dan.addAction(new HealingMagic("デンチ", 20, 10));
 
        //アクションの順序を決める
        order.add(denchu);
        if(dan.getWin() <= 0){
         System.out.println("ダンさんはたおれた。");
        }
        order.add(dan);
    }
 
    void showStatus() { //全キャラクタのステータスを表示（テスト用）
        for(var ch : order) {
            ch.openStatus();
        }
    }
 
    void battle() { //１ターン実行する
        for(var ch : order) {
            ch.act(order);
        }
    }
 
    public static void main(String[] args) {
     var master = new GameMaster();
 
     for(var i=0; i<3; i++) {
          master.showStatus();
          master.battle();
      }
 }
 
 
 }

 interface Hand {
    String name();
 }

 class CommandSelector {
    ArrayList<String> commands;
    Scanner scanner;

    CommandSelector() {
        scanner = new Scanner(System.in);
        commands = new ArrayList<>();
    }

    void addCommand(String command_name) {
        commands.add(command_name);
    }

    //promptを表示した上で，ユーザの選択を待つ
    int waitForUsersCommand(String prompt) {
        var index = 0;
        System.out.println(prompt);
        for(var command : commands) { //選択肢をprint
            System.out.println(index + ":" + command);
            index += 1;
        }

        //標準入力から数値を入力
        while(true) {
            int target_index = scanner.nextInt();

            if (target_index >= 0 && target_index < commands.size()) {
                return target_index;
            }
        }        
    }
}
class Enemy extends Character {
    Enemy(String name, int win, int lose) {
        super(name, win, lose);
    }
 
    @Override
    void act(ArrayList<Character> targets) {
        Random random = new Random();
        int command = random.nextInt(3);
        getHands().get(command);
    }
 }

 class Rock implements Hand{
    
 }