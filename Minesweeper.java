package LandMineSearch;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class Minesweeper extends JFrame{
    private static final int SIZE10 = 500;
    private static final int SIZE20 = 700;
    private static final int SIZE30 = 900;
    private static final int MINE = 10;
    private static int realMineNum = 0;
    private static final int N = 60;
    private static int stop = 0;
    private static Cell[] cellStorage = new Cell[8];

    private int Row;
    private int Col;
    private Cell[][] cells;

    private JFrame  frame;
   
    private final ActionListener actionListener = actionEvent -> {
        Object event = actionEvent.getSource();
            setCell((Cell) event);       
    };
    
    private class Cell extends JButton {
        private final int row;
        private final int col;
        private int value;

        Cell(final int row, final int col,
             final ActionListener actionListener) {
            this.row = row;
            this.col = col;
            addActionListener(actionListener);
            setText("");      
        }

        void setValue(int value) {
            this.value = value;
        }

        boolean trueMine() {
            return value == MINE;
        }

        void reveal() {
            setEnabled(false);
            if(trueMine()) {
            	setText(" ");
            	setBackground(Color.RED);
            }else {
            	setText(String.valueOf(value));
            	setForeground(Color.GRAY);
            }
            
        }
        
        void reset() {
            setValue(0);
            setEnabled(true);
            setText("");
        }
        
        void nbrCntUpdate() {
            getnbrs(cellStorage);
            for (Cell nbr : cellStorage) {
                if (nbr == null) {
                    break;
                }
                if (nbr.trueMine()) {
                    value++;
                }
            }
        }

        void getnbrs(final Cell[] container) {
            for (int i = 0; i < cellStorage.length; i++) {
                cellStorage[i] = null;
            }

            int index = 0;

            for (int cancelRow = -1; cancelRow <= 1; cancelRow++) {
                for (int cancelCol = -1; cancelCol <= 1; cancelCol++) {
                    if (cancelRow == 0 && cancelCol == 0) {
                        continue;
                    }
                    int rowValue = row + cancelRow;
                    int colValue = col + cancelCol;

                    if (rowValue < 0 || rowValue >= Row
                        || colValue < 0 || colValue >= Col) {
                        continue;
                    }

                    container[index++] = cells[rowValue][colValue];
                }
            }
        }
    }
    

    private Minesweeper(final int Row, final int Col){
    
    	stop = 0;
        this.Row = Row;
        this.Col = Col;
        cells = new Cell[Row][Col];

        frame = new JFrame("Minesweeper");
        if(Row == 10) {
        	frame.setSize(SIZE10, SIZE10);
        }
        else if(Row == 20) {
        	frame.setSize(SIZE20, SIZE10);
        }
        else {
        	frame.setSize(SIZE30, SIZE20);
        }
       
        frame.setLayout(new BorderLayout());

        
        resetBoard();

        
        for(Cell[] i:cells) {
        	for(Cell j:i) {
                j.addMouseListener(
                	new MouseAdapter() {
            	       	public void mouseReleased(MouseEvent mouseEvent) {
            	       		if(SwingUtilities.isRightMouseButton(mouseEvent)) {
            	       			if(j.getBackground() == Color.BLUE) {
            	       				j.setBackground(null);
                    			}else {
           	        				j.setBackground(Color.BLUE);
           	        			}
           	        		}
           	        	}
           	        }			
                );
        	}
        }
        
        // 실 지뢰개수
        for(int row = 0; row < Row; row++) {
        	for(int col = 0; col < Col; col++) {
        		if(cells[row][col].trueMine() == true) {
        			realMineNum ++;
        		}
        	}
        }          
        
        makeMenu();
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
  

    private void makeMenu() {
     	
    	JMenuBar mb = new JMenuBar();
    	JMenu gameMenu = new JMenu("게임");
    	JMenu fileMenu = new JMenu("파일");
    	JMenu lookMenu = new JMenu("보기");
    	JMenuItem ng = new JMenuItem("새 게임");
    	JMenuItem ls = new JMenuItem("레벨 선택");
    	JMenuItem sv = new JMenuItem("저장");
    	JMenuItem op = new JMenuItem("불러오기");
    	JMenuItem cc = new JMenuItem("색상 변경");

    	gameMenu.add(ng);
    	gameMenu.add(ls);
    	fileMenu.add(sv);
    	fileMenu.add(op);
    	lookMenu.add(cc);
    	
    	mb.add(gameMenu);
    	mb.add(fileMenu);
      	mb.add(lookMenu);
      	
    	JButton mineNum = new JButton("지뢰수 : " + realMineNum);
    	mineNum.setEnabled(false);
    	mb.add(mineNum);
  
    	
 
    	// timer
    	
    	JTextField countText = new JTextField(8);
    	countText.setText("시작버튼을 누르세요.");
    	countText.setEditable(false);

    	Timer t = new Timer(1000, new ActionListener() {
    	    private int hr;
    	    private int min;
    	    private int sec;
    	    private String hour;
    	    private String minute;
    	    private String second;
    	    
    	    public void actionPerformed(ActionEvent e) {

    	        NumberFormat format = new DecimalFormat("00");
    	        if (sec == N) {
    	            sec = 00;
    	            min++;
    	        }

    	        if (min == N) {
    	            min = 00;
    	            hr++;
    	        }
    	        hour = format.format(hr);
    	        minute = format.format(min);
    	        second = format.format(sec);
    	        countText.setText(String.valueOf(hour + ":" + minute + ":" + second));
    	        sec++;
    	        
    	        if(stop == 1) {
    	        	sec--;
    	        }
    	    }
    	    
    	});
    	

        final JToggleButton btn = new JToggleButton("start");
        btn.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
            
                if (btn.isSelected()) {
                    t.start();
                    btn.setText("stop");
                } else {
                    t.stop();
                    btn.setText("start");
                }

            }
        });
        
    	t.setInitialDelay(0);

    	mb.add(countText);
    	mb.add(btn);
    	frame.add(mb, BorderLayout.NORTH);
    	

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
				frame.dispose();
				if(Row == 10) {
					new Minesweeper(10,10);
				}
				else if(Row == 20) {
					new Minesweeper(20,10);
				}
				else {
					new Minesweeper(30,20);
				}
			}
		};
		ng.addActionListener(newGame);
		
		// menu - save
		ActionListener save = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File myObj = new File("C:\\Users\\Suemin\\Minesweeper_jam.dat");
				try {
					if(myObj.createNewFile()) {
						System.out.println("Game saved at C:\\Users\\Suemin\\" + myObj.getName());
					} else {
						System.out.println("Game saved.");
					}
				}catch(IOException e1) {
					
				}
			}
		};
		sv.addActionListener(save);
		
		// menu - open
		ActionListener open= new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame window = new JFrame();
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(window);
				
				if(result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.println(selectedFile+ " was loaded.");
				}
			}
		};
		op.addActionListener(open);
		
		// menu - change color
		ActionListener changeColor = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        for (int row = 0; row < Row; row++) {
		            for (int col = 0; col < Col; col++) {
		                cells[row][col].setBackground(Color.WHITE);
		            }
		        }
			}
		};
		cc.addActionListener(changeColor);

    }
    
    // new Window    
	class newWindow extends JFrame {
	    newWindow() {
	        setTitle("레벨 선택");
	        
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
					frame.dispose();
					dispose();
					new Minesweeper(10,10);
					
				}
			};
			easy.addActionListener(openEasy);
			
			// 중급창 열기
	        ActionListener openMedium = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
					dispose();
					new Minesweeper(20,10);
					
				}
			
			};
			medium.addActionListener(openMedium);
			
			// 고급창 열기
	        ActionListener openHard = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
					dispose();
					new Minesweeper(30,20);
					
				}
			};
			hard.addActionListener(openHard);

	    };
	    

	}
    
    private void resetBoard() {
        Container grid = new Container();
        grid.setLayout(new GridLayout(Row, Col));

        for (int row = 0; row < Row; row++) {
            for (int col = 0; col < Col; col++) {
                cells[row][col] = new Cell(row, col, actionListener);
                grid.add(cells[row][col]); 
            }
        }
        
        createMine();

        frame.add(grid, BorderLayout.CENTER);  
    }

    
    private void resetAllCells() {
        for (int row = 0; row < Row; row++) {
            for (int col = 0; col < Col; col++) {
                cells[row][col].reset();
                cells[row][col].setBackground(null);
            }
        }
    }

    private void createMine() {
    	realMineNum = 0;
        resetAllCells();

        
        final Random random    = new Random();
        
        Set<Integer> locations = new HashSet<>(Row * Col);
        for (int row = 0; row < Row; row++) {
            for (int col = 0; col < Col; col++) {
              locations.add(row * Row + col);
            }
        }
        for(int col = 0; col < Col; col++) {
        	for(int row = 0; row < Row; row++) {
        		locations.add(col * Col + row);
        	}
        }

        // 지뢰 초기화
        for (int index = 0; index < Row; index++) {
            int choice = random.nextInt(locations.size());
            int row    = choice / Row;
            int col    = choice % Col;
            cells[row][col].setValue(MINE);
            locations.remove(choice);
        }

        // neighbor cells 초기화
        for (int row = 0; row < Row; row++) {
            for (int col = 0; col < Col; col++) {
                if (!cells[row][col].trueMine()) {
                    cells[row][col].nbrCntUpdate();
                }
            }
        }
    }
    

    private void setCell(Cell cell) {
        if (cell.trueMine()) {
            cell.setForeground(Color.RED);
            cell.reveal();
            stop = 1;
            revealMine("지뢰를 밟아버렸습니다!");
        }

        else {
            cell.reveal();
        }  
        
        checkWon();    
    }
    

    private void revealMine(String message) {
        for (int row = 0; row < Row; row++) {
            for (int col = 0; col < Col; col++) {

        	    if(cells[row][col].trueMine()) {
        	    	cells[row][col].reveal();
        	    }
                cells[row][col].setEnabled(true);

           }
        }

        JOptionPane.showMessageDialog(
                frame, message, "Game over",
                JOptionPane.ERROR_MESSAGE
        );
    
    }

    private void checkWon() {
        boolean win = true;
        loop:
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                if (!cell.trueMine() && cell.isEnabled()) {
                    win = false;
                    break loop;
                }
            }
        }

        if (win) {
            JOptionPane.showMessageDialog(
                    frame, "축하합니다! 지뢰를 전부 제거했습니다!", "게임 종료",
                    JOptionPane.INFORMATION_MESSAGE
            );
            stop = 1;
        }

    }


    public static void main(String[] args) {
        final int Row = 10;
        final int Col = 10;
        
        new Minesweeper(Row, Col);

    }
}