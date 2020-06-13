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
	
	Font f1 = new Font("����", Font.PLAIN, 15);
	// �ʱ� ���̾ƿ�
	void makeLayoutEasy() {
		p = new JPanel();
		setTitle("����ã��");
		p.setLayout(new GridLayout(10,10));
		
		for(int i = 0; i < 100; i++) {
			button = new JButton("");
			p.add(button);
		}
		add(p);
	}
	
	
	// �߱� ���̾ƿ�
	void makeLayoutMedium() {
		p = new JPanel();
		setTitle("����ã��");
		p.setLayout(new GridLayout(10,20));
		
		for(int i = 0; i < 200; i ++) {
			button = new JButton("");
			
			p.add(button);
		}
		add(p);
	}
	
	// ��� ���̾ƿ�
	void makeLayoutHard() {
		p = new JPanel();
		setTitle("����ã��");
		p.setLayout(new GridLayout(20,30));
		
		for(int i = 0; i < 600; i ++) {
			button = new JButton("");	

			p.add(button);
		}
		add(p);

	}
	
	// �޴���
	void makeMenu() {
		JMenuBar mb = new JMenuBar();
		
		JMenu m1 = new JMenu("����");
		JMenuItem ng = new JMenuItem("�� ����");
		m1.add(ng);
		JMenuItem ls = new JMenuItem("���� ����");
		m1.add(ls);
		JMenuItem eg = new JMenuItem("�����ϱ�");
		m1.add(eg);
		
		JMenu m2 = new JMenu("����");
		JMenuItem sv = new JMenuItem("����");
		m2.add(sv);
		JMenuItem op = new JMenuItem("�ҷ�����");
		m2.add(op);
		
		JMenu m3 = new JMenu("����");
		m3.add(new JMenuItem("����"));
		
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
	    // ��ư�� �������� ��������� �� â�� ������ Ŭ����
	    newWindow() {
	        setTitle("���� ����");
	        // ����, ���⼭ setDefaultCloseOperation() ���Ǹ� ���� ���ƾ� �Ѵ�
	        // �����ϰ� �Ǹ� �� â�� ������ ��� â�� ���α׷��� ���ÿ� ������
	        
	        JPanel NewWindowContainer = new JPanel();
	        setContentPane(NewWindowContainer);
	        
	        JButton easy = new JButton("�ʱ�");
	        JButton medium = new JButton("�߱�");
	        JButton hard = new JButton("���");
	        
	        NewWindowContainer.add(easy);
	        NewWindowContainer.add(medium);
	        NewWindowContainer.add(hard);
	        
	        setSize(300,100);
	        setResizable(false);
	        setVisible(true);
	        
	        // �ʱ�â ����
	        ActionListener openEasy = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new LandMineSearch(1);
					
				}
			};
			easy.addActionListener(openEasy);
			
			// �߱�â ����
	        ActionListener openMedium = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					new LandMineSearch(2);
					
				}
			
			};
			medium.addActionListener(openMedium);
			
			// ���â ����
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
