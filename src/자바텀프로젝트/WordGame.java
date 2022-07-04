package 자바텀프로젝트;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
class Page01 extends JPanel { // 1번째 페이지
	private JButton jButton1;
	private JButton jButton2;
	private JLabel label;
	private Test win;

	public Page01(Test win) {
		this.win = win;
		setLayout(null);

		TitledBorder k = new TitledBorder(new LineBorder(Color.white));

		label = new JLabel("Word Game");
		label.setBounds(125, 160, 350, 50);
		label.setFont(new Font("Times", Font.PLAIN, 33));
		label.setForeground(new Color(255, 255, 255, 200));
		label.setVisible(true);
		this.add(label);

		Color color = new Color(145, 82, 77);
		setBackground(color);
		setBorder(k);
		jButton1 = new JButton("게임 입장");
		jButton1.setBackground(new Color(255, 255, 255));
		jButton1.setFont(new Font("Times", Font.BOLD, 15));
		// jButton1.setForeground(Color.black);
		jButton1.setSize(120, 40);
		jButton1.setLocation(140, 230);
		this.add(jButton1);

		jButton2 = new JButton("게임방법");
		jButton2.setBackground(new Color(234, 225, 228));
		jButton2.setFont(new Font("Times", Font.BOLD, 15));
		jButton2.setSize(120, 40);
		jButton2.setLocation(140, 280);
		this.add(jButton2);

		JLabel l = new JLabel("만든 사람 : 유리>_<♥");
		l.setBounds(300, 580, 100, 20);
		l.setFont(new Font("Times", Font.ITALIC, 10));
		l.setForeground(new Color(255, 255, 255, 180));
		this.add(l);

		jButton1.addActionListener(new MyActionListener());
		jButton2.addActionListener(new MyActionListener1());

	}

	class MyActionListener implements ActionListener { // 버튼을 누르면 2번째 페이지 호출
		@Override
		public void actionPerformed(ActionEvent e) {
			win.change("page02");
		}
	}

	class MyActionListener1 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			win.change("page03");

		}

	}
}

@SuppressWarnings("serial")
class Page03 extends JPanel {
	private JButton b1 = new JButton("돌아가기");
	private JLabel l1 = new JLabel("          How to play      ");
	private JLabel l2 = new JLabel(
			"<html>1. 게임 입장 버튼을 누르면 게임 창으로 전환이 됩니다.<br><br>2. 자신이 원하는 레벨에 맞게 레벨을 선택해 주세요.<br> → 레벨이 높을 수록 단어 생성 속도가 빠릅니다.<br><br>3. 화면에 뜨는 단어를 화면 아래 단어 입력창에 입력 후<br>키보드의 엔터 버튼을 누르세요.<br> → 단어를 잘못 입력할 시 하트가 하나 소모 됩니다.<br><br>4. 타임 오버나 하트를 다 썼을 시 게임이 종료 됩니다.");
	private Test win;

	public Page03(Test win) {
		this.win = win;
		setLayout(null);

		Color color = new Color(204, 102, 102, 190);
		setBackground(color);

		b1.setBounds(150, 390, 100, 30);
		b1.setFont(new Font("Times", Font.ITALIC, 13));
		add(b1);
		b1.addActionListener(new MyActionListener());

		l1.setBounds(110, 95, 400, 50);
		l1.setForeground(new Color(255, 255, 255));
		l1.setFont(new Font("Times", Font.ITALIC, 20));
		add(l1);

		l2.setBounds(45, 135, 400, 200);
		l2.setForeground(new Color(255, 255, 255));
		l2.setFont(new Font("Times", Font.ITALIC, 13));
		add(l2);
	}

	class MyActionListener implements ActionListener { // 버튼을 누르면 2번째 페이지 호출
		@Override
		public void actionPerformed(ActionEvent e) {
			win.change("page01");
		}
	}
}

@SuppressWarnings("serial")
class Page02 extends JPanel implements ActionListener, ListSelectionListener { // 스윙 응용프로그램의 프레임을 만들기 위해 JFrame클래스 상속
	private JLabel scoreboard = new JLabel("Score :"); // score board 문자열을 출력하기 위해 생성
	private JLabel score = new JLabel("0"); // 점수를 출력하기 위해 생성
	private JLabel life = new JLabel("Life "); // 문자열 Life를 출력하기 위해 생성
	private JLabel heart = new JLabel("♥♥♥"); // 유저의 목숨을 출력하기 위해 생성
	private JTextField input = new JTextField(); // 단어 입력을 위한 input 생성
	private JButton enter = new JButton("Enter"); // 입력완료버튼 enter 생성
	private Game game = new Game();
	private Thread t = new Thread(game);
	private JButton b2 = new JButton("게임종료");
	private JButton st1 = new JButton("게임시작");
	private JPanel gameover = new JPanel();
	private JLabel overlabel = new JLabel("Game Over!");
	private Test win;
	private String levellist[] = { "Level 1", "Level 2", "Level 3" };
	private JList list = new JList(levellist);
	private int speed;
	TitledBorder k = new TitledBorder(new LineBorder(Color.black));
	private JLabel time = new JLabel("TIME "); // 문자열 TIME을 출력하기 위해 생성
	private JLabel timerLable = new JLabel(); // timerLabel 생성
	private TimerRunnable gs = new TimerRunnable(timerLable); // 타이머 스레드로 사용할 객체 생성, 타이머 값을 출력할 레이블을 생성자에게 전달
	private Thread th = new Thread(gs); // 스레드 객체 생성

	class TimerRunnable implements Runnable { // 타이머 구현을 위해 Runnable 인터페이스 이용하여 클래스 구현
		private JLabel timerLabel; // 타이머 값이 출력된 레이블
		private int n;

		public TimerRunnable(JLabel timerLabel) {
			this.timerLabel = timerLabel; // 타이머 초 감소 카운트를 출력할 timerLabel
		}

		@Override
		public void run() { // 스레드 코드작성
			n = 50; // 타이머를 50초로 설정
			while (true) {// for(int i=n;i>=0;i--){ // 무한루프를 실행하여 1초에 한 번식 n을 감소시켜 timerLabel에 출력
				timerLabel.setText(Integer.toString(n)); // timerLabel에 카운트 값 출력
				n--; // 카운트 감소
				if (n == -2) { // n이 -1이 되면 타이머가 멈추게 설정
					t.interrupt();
					th.interrupt();
					gameover.setVisible(true);
					game.setVisible(false);
					input.setVisible(false);
					enter.setVisible(false);
					time.setVisible(false);
					timerLabel.setVisible(false);
					break;
				}
				try {
					t.join();
					Thread.sleep(1000); // 1초동안 잠을 잔 후 깨어남
				} catch (InterruptedException e) {
					return; // 예외가 발생하면 리턴 if문으로 가서 -1이 되면 타이머가 멈춤
				}
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == list) {
			switch (list.getSelectedIndex()) {
			case 0:
				speed = 1800;
				break;
			case 1:
				speed = 1200;
				break;
			case 2:
				speed = 700;
				break;
			default:
				break;
			}
		}

	}

	public Page02(Test win) {
		setLayout(null);
		this.win = win;

		this.init();
		game.setVisible(true);
		list.setVisible(true);
		st1.addActionListener(new MyActionListener());
		input.addActionListener(this);
		b2.addActionListener(new MyActionListener1());

	}

	class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(st1)) {
				score.setText("0");
				heart.setText("♥♥♥");
				th.start();
				time.setVisible(true);
				timerLable.setVisible(true);
				list.setVisible(false);
				st1.setVisible(false);
				if (gameover.isVisible()) {
					gameover.setVisible(false);
					game.revalidate();
					game.repaint();

				}
			}
			new Thread(game).start();
		}
	}

	public

	class MyActionListener1 implements ActionListener { // 버튼을 누르면 1번째 페이지 호출
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(b2)) {
				System.exit(0);
			}
		}
	}

	public void init() {
		setBackground(new Color(153, 102, 102)); // 컨텐트팬의 색을 핑크로 지정
		// setLayout(null); // 컨텐트팬의 배치관리자 제거

		st1.setBounds(150, 5, 100, 20);
		st1.setFont(new Font("Times", Font.ITALIC, 13));
		add(st1);

		/* time */
		time.setFont(new Font("Times", Font.ITALIC, 12)); // 폰트와 폰트크기 지정
		time.setForeground(new Color(255, 255, 255, 230));
		time.setBounds(3, 545, 50, 30); // time의 위치 (3,530)과 크기 50*30으로 지정
		add(time); // time을 컨텐트팬에 부착

		/* timerLabel */
		timerLable.setFont(new Font("Times", Font.ITALIC, 12)); // 폰트와 폰트크기 지정
		timerLable.setForeground(new Color(255, 255, 255, 230));
		timerLable.setBounds(40, 545, 50, 30); // timerLabel 위치 (40,530)과 크기 50*30으로 지정
		add(timerLable); // timerLabel을 컨텐트팬에 부착

		/* game */
		game.setVisible(true);
		game.setBackground(new Color(153, 102, 102));
		// game.setBorder(k);
		game.setBounds(1, 30, 397, 520);
		add(game);

		list.addListSelectionListener(this);
		list.setSelectedIndex(0);
		list.setBorder(k);
		list.setBounds(150, 30, 100, 52);
		list.setFont(new Font("Times", Font.ITALIC, 13));
		add(list);

		gameover.setBounds(1, 30, 397, 530);
		gameover.setBackground(new Color(153, 102, 102));
		gameover.setVisible(false);
		add(gameover);
		gameover.setLayout(null);

		overlabel.setBounds(105, 200, 300, 50);
		overlabel.setForeground(new Color(255, 255, 255, 220));
		overlabel.setFont(new Font("Times", Font.ITALIC, 40));
		gameover.add(overlabel);

		b2.setBounds(150, 270, 100, 30);
		gameover.add(b2);

		/* score board */
		scoreboard.setBounds(1, 1, 50, 30); // score board의 위치(1,1)과 크기 70*30으로 지정
		scoreboard.setFont(new Font("Times", Font.ITALIC, 15));
		scoreboard.setForeground(new Color(255, 255, 255, 230));
		add(scoreboard); // score board를 컨텐트팬에 부착

		/* score */
		score.setBounds(48, 3, 50, 28); // score의 위치 (48,1)과 크기 50*30으로 지정
		score.setFont(new Font("Times", Font.ITALIC, 15));
		score.setForeground(new Color(255, 255, 255, 230));
		add(score); // score를 컨텐트팬에 부착

		/* life */
		life.setBounds(300, 1, 50, 30); // life의 위치 (275,1)과 크기 50*30으로 지정
		life.setFont(new Font("Times", Font.ITALIC, 15));
		life.setForeground(new Color(255, 255, 255, 230));
		add(life); // life를 컨텐트팬에 부착

		/* heart */
		heart.setForeground(new Color(204, 000, 000)); // ♥의 색을 빨간색으로 지정
		heart.setFont(new Font("Times", Font.ITALIC, 22)); // 폰트와 폰트크기 지정
		heart.setBounds(335, 3, 100, 28); // heart의 위치 (300,1)과 크기 100*33 지정
		add(heart); // heart를 컨텐트팬에 부착

		/* 단어 입력창 */
		input.setBounds(1, 567, 335, 30); // input의 위치 (1,550)과 크기 335*30 지정
		add(input); // input을 컨텐트팬에 부착

		/* 입력완료버튼 */
		enter.setBounds(330, 567, 70, 32); // 버튼의 위치(330,550)과 크기 70*30 지정
		enter.setFont(new Font("Times", Font.ITALIC, 15));
		add(enter); // enter를 컨텐트팬에 부착

	}

	public void actionPerformed(ActionEvent e) { // 엔터 버튼을 누르기 위해 이벤트 리스너 작성
		int temp = Integer.valueOf(score.getText()); // 올바른 단어를 입력한다면 score의 라벨의 문자열을 정수로 바꿈
		if (e.getSource() == input) { //
			if (game.removeWord(input.getText())) // 입력한 단어와 제거된 단어가 같다면
				temp += 100; // 100점 증가
			else { // 다르다면
				temp -= 100; // 100점 감소
				int life = heart.getText().length(); // heart의 텍스트박스를 읽어와서 문자열 길이를 읽음
				if (life > 0) { // 문자열 길이가 0보다 크다면
					String h = heart.getText().substring(0, life - 1); // 한개씩 뒤에서부터 문자(♥) 삭제
					heart.setText(h); // 삭제가 완료된 heart 출력
					switch (life) {
					case 1:
						time.setVisible(false);
						timerLable.setVisible(false);
						game.setVisible(false);
						gameover.setVisible(true);
						input.setVisible(false);
						enter.setVisible(false);
						t.interrupt();
						break;

					}
				}

			}
			score.setText(temp + ""); // JLabel클래스로 지정한 score의 내용을 설정. 위 조건문에서 계산한 내용을 String으로 변환
			input.setText(""); // 단어 입력 후 단어 입력창 공백으로 초기화 함
		}
	}

	class Game extends JPanel implements Runnable { // Canvas 클래스 상속과 단어를 출력하기 위해 Runnable 인터페이스 구현

		private String[] word = { "yuri", "java", "game", "word", "memo", "point", "start", "finish", "love", "bonus",
				"bad", "good", "time", "print", "music", "system", "dog", "cat", "bottle", "phone", "robot", "green",
				"elephant", "include", "sky", "game", "love", "max", "knife", "glass", "class", "art", "smart", "bell",
				"carry", "climb", "between", "blow", "album", "ago", "among", "animal", "any", "box", "and", "board",
				"body", "child", "city", "boy", "bridge", "clean", "club", "coat", "bright", "coin", "coffee", "cold",
				"chance", "chalk", "chair", "cheap", "blue", "before", "bowl", "aunt", "as", "away", "bicycle",
				"church", "card", "hold", "chose", "come", "drink", "give", "get", "hurt", "lay", "had", "feed", "lend",
				"met", "sing", "throw", "wet", "tell", "set", "wind", "wear", "write", "spend", "stand", "worn", "win",
				"sweep", "account", "achieve", "across", "accept", "above", "ability", "abuse", "abnormal", "absurd",
				"absent" }; // 출력될 단어
		private Vector<Word> v = new Vector<Word>(); // Word의 객체만 다루는 벡터 생성

		public Game() {
			// this.setFont(new Font(null, Font.PLAIN, 30)); // 출력될 단어의 폰트와 글자 크기 지정
			// this.setBackground(Color.pink);
		}

		/*
		 * public void paint(Graphics g) { // Game의 내부를 그리는 메소드 Iterator<Word> it =
		 * v.iterator(); // 벡터 v의 요소를 순차 검색할 Iterator 객체 리턴
		 * 
		 * while (it.hasNext()) { // it로 벡터의 끝까지 반복 v의 모든 단어 출력 Word w = it.next(); //
		 * it가 가리키 g.setColor(Color.white); g.setFont(new Font("Times", Font.PLAIN,
		 * 15)); g.drawString(w.s, w.p.x, w.p.y); // Canvas에 단어를 출력하기위해 drawString 메소드
		 * 사용, 좌표(x,y)에 문자열 s를 출력
		 * 
		 * } }
		 */
		public void paint(Graphics g) { // Game의 내부를 그리는 메소드
			Iterator<Word> it = v.iterator(); // 벡터 v의 요소를 순차 검색할 Iterator 객체 리턴

			while (it.hasNext()) { // it로 벡터의 끝까지 반복 v의 모든 단어 출력
				Word w = it.next(); // it가 가리키

				g.setFont(new Font("Times", Font.PLAIN, 15));
				int width = g.getFontMetrics().stringWidth(w.s);
				System.out.print(w);
				g.setColor(new Color(153, 102, 102));
				g.fillRect(w.p.x - 10, w.p.y - 12, width + 13, 22);
				g.setColor(Color.white);
				g.setFont(new Font("Times", Font.PLAIN, 15));
				g.drawString(w.s, w.p.x, w.p.y); // Canvas에 단어를 출력하기위해 drawString 메소드 사용, 좌표(x,y)에 문자열 s를 출력

			}
		}

		private Point Random() { // 단어를 무작위로 출력하기 위해 좌표 (x,y)생성, Point 객체에 리턴
			int x = (int) (Math.random() * getWidth()); // 랜덤 좌표 x, 폭의 랜덤 좌표 Math클래스를 이용함
			int y = (int) (Math.random() * getHeight()); // 랜덤 좌표 y, 높이 랜덤 좌표

			return new Point(x % 350, y % 490 + 15); // 좌표를 x는 350-1, y는 30부터 470-1까지 설정 490 15
		}

		private int RandomIndex() { // 단어를 출력하기 위해 word 배열의 인덱스를 랜덤으로 생성
			int a = (int) (Math.random() * word.length);
			return a; // 배열의 크기를 알아내기 위해 length필드 사용 return a;
		}

		public void makeWords() {
			Point p = this.Random(); // Random 메소드를 호출하여 랜덤 좌표를 가져옴
			int i = this.RandomIndex();

			Iterator<Word> it = v.iterator(); // 벡터 v의 요소를 순차 검색할 Iterator 객체 리턴
			while (it.hasNext()) {
				Word w = it.next();
				if (word[i].equals(w.s)) {
					v.remove(w);
					break;
				}
			}
			v.add(new Word(p, word[i]));
			repaint(); // 입력한 단어가 사라진 후 화면을 다시 구성

		}

		public boolean removeWord(String str) { // 단어입력시 단어를 삭제하는 메소드
			Iterator<Word> it = v.iterator(); // 벡터 v의 요소를 순차 검색할 Iterator 객체 리턴
			boolean flag = false;

			while (it.hasNext()) { // it로 벡터의 끝까지 반복 v의 모든 단어를 읽음
				Word w = it.next(); // it가 가리키는 요소 Word 클래스 리턴
				if (str.equals(w.s)) { // 입력한 단어와 배열의 문자열이 같은지를 확인함
					v.remove(w); // 같다면 삭제
					flag = true; // 삭제 공시 true 리턴
					repaint(); // 입력한 단어가 사라진 후 화면을 다시 구성
					break;
				}
			}
			return flag; // 다르다면 false 리턴
		}

		public void run() { // 스레드 코드 작성
			// 무한루프를 실행시켜 makeWords()를 호출, 1초 마다 단어를 출력
			for (int i = 0; i < word.length; i++)
				try {
					makeWords();
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					return; // 예외가 발생하면 리턴
				}
		}
	}

}

class Word { // 단어의 좌표와 단어 출력을 위한 Word 클래스
	Point p; // 단어 좌표를 위해 Point 클래스 사용
	String s; // 단어 출력을 위한 String 클래스 사용

	public Word(Point p, String s) {
		this.p = p;
		this.s = s;

	}
}

@SuppressWarnings("serial")
class Test extends JFrame {
	public Page01 page01 = null;
	public Page02 page02 = null;
	public Page03 page03 = null;

	public void change(String panelName) { // 페이지 1번과 2번 변경 후 재설정

		if (panelName.equals("page01")) {
			getContentPane().removeAll();
			getContentPane().add(page01);
			revalidate();
			repaint();
		} else if (panelName.equals("page02")) {
			getContentPane().removeAll();
			getContentPane().add(page02);
			revalidate();
			repaint();
		} else if (panelName.equals("page03")) {
			getContentPane().removeAll();
			getContentPane().add(page03);
			revalidate();
			repaint();
		}

	}

}

public class WordGame {
	public static void main(String[] args) {

		Test win = new Test();
		try {

			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

		} catch (Exception e) {

			e.printStackTrace();

		}

		win.setTitle("♥♥♥♥ 단어 없애기 게임 ♥♥♥♥");
		win.page01 = new Page01(win);
		win.page02 = new Page02(win);
		win.page03 = new Page03(win);

		win.add(win.page01);
		win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		win.setSize(400, 620);
		win.setVisible(true);
		win.setResizable(false); // 창의 크기를 고정
		win.setLocationRelativeTo(null);
	}
}