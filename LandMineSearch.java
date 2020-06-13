package LandMineSearch;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;


public class LandMineSearch extends JFrame {
	
	
	JButton button;
	JPanel p;
	
	LandMineSearch(int level){
		if(level == 1) {
			
			makeLayoutEasy();
			setSize(350,350);
			
		}
		else if(level == 2) {
		
			makeLayoutMedium();
			setSize(600,400);
		}
		else {
			makeLayoutHard();
			setSize(800,600);
		}
		
		makeMenu();
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new LandMineSearch(1);
	}
	
	Font f1 = new Font("돋움", Font.PLAIN, 15);
	// 초급 레이아웃
	void makeLayoutEasy() {
		p = new JPanel();
		setTitle("지뢰찾기");
		p.setLayout(new GridLayout(10,10));
		
		for(int i = 0; i < 100; i++) {
			button = new JButton("");
			p.add(button);
		}
		add(p);
	}
	
	
	// 중급 레이아웃
	void makeLayoutMedium() {
		p = new JPanel();
		setTitle("지뢰찾기");
		p.setLayout(new GridLayout(10,20));
		
		for(int i = 0; i < 200; i ++) {
			button = new JButton("");
			
			p.add(button);
		}
		add(p);
	}
	
	// 고급 레이아웃
	void makeLayoutHard() {
		p = new JPanel();
		setTitle("지뢰찾기");
		p.setLayout(new GridLayout(20,30));
		
		for(int i = 0; i < 600; i ++) {
			button = new JButton("");	

			p.add(button);
		}
		add(p);

	}
	
	// 메뉴바
	void makeMenu() {
		JMenuBar mb = new JMenuBar();
		
		JMenu m1 = new JMenu("게임");
		JMenuItem ng = new JMenuItem("새 게임");
		m1.add(ng);
		JMenuItem ls = new JMenuItem("레벨 선택");
		m1.add(ls);
		JMenuItem eg = new JMenuItem("종료하기");
		m1.add(eg);
		
		JMenu m2 = new JMenu("파일");
		JMenuItem sv = new JMenuItem("저장");
		m2.add(sv);
		JMenuItem op = new JMenuItem("불러오기");
		m2.add(op);
		
		JMenu m3 = new JMenu("도움말");
		m3.add(new JMenuItem("도움말"));
		
		mb.add(m1);
		mb.add(m2);
		mb.add(m3);
		this.setJMenuBar(mb);
		
		// menu - levelSelect
		ActionListener levelSelect = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new newWindow();
				
			}
		};
		ls.addActionListener(levelSelect);
		
		// menu - newGame 
		ActionListener newGame = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				new LandMineSearch(1);
			}
		};
		ng.addActionListener(newGame);
		
		// menu - save
		ActionListener save = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			}
		};
		sv.addActionListener(save);
		
		// menu - open
		ActionListener open= new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		};
		op.addActionListener(open);
		
		
		// menu - endGame
		ActionListener endGame = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		eg.addActionListener(endGame);
	}
	
	
	class newWindow extends JFrame {
	    // 버튼이 눌러지면 만들어지는 새 창을 정의한 클래스
	    newWindow() {
	        setTitle("레벨 선택");
	        // 주의, 여기서 setDefaultCloseOperation() 정의를 하지 말아야 한다
	        // 정의하게 되면 새 창을 닫으면 모든 창과 프로그램이 동시에 꺼진다
	        
	        JPanel NewWindowContainer = new JPanel();
	        setContentPane(NewWindowContainer);
	        
	        JButton easy = new JButton("초급");
	        JButton medium = new JButton("중급");
	        JButton hard = new JButton("고급");
	        
	        NewWindowContainer.add(easy);
	        NewWindowContainer.add(medium);
	        NewWindowContainer.add(hard);
	        
	        setSize(300,100);
	        setResizable(false);
	        setVisible(true);
	        
	        // 초급창 열기
	        ActionListener openEasy = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new LandMineSearch(1);
					
				}
			};
			easy.addActionListener(openEasy);
			
			// 중급창 열기
	        ActionListener openMedium = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					new LandMineSearch(2);
					
				}
			
			};
			medium.addActionListener(openMedium);
			
			// 고급창 열기
	        ActionListener openHard = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					new LandMineSearch(3);
					
				}
			};
			hard.addActionListener(openHard);

	    };
	    
	}
	
}
