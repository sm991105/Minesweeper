package LandMineSearch;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.awt.event.MouseAdapter;
import java.util.*;


public class Minesweeper extends JFrame{
    // The value assigned to cells marked as mines. 10 works
    // because no cell will have more than 8 neighbouring mines.
    private static final int MINE = 10;
    // The size in pixels for the frame.
    private static final int SIZE10 = 500;
    private static final int SIZE20 = 700;
    private static final int SIZE30 = 900;
    private static int realMineNum = 0;
    private static final int N = 60;
    private static int stop = 0;

    // This fixed amount of memory is to avoid repeatedly declaring
    // new arrays every time a cell's neighbours are to be retrieved.
    private static Cell[] reusableStorage = new Cell[8];

    private int Row;
    private int Col;
    private Cell[][] cells;

    private JFrame  frame;
   
    private final ActionListener actionListener = actionEvent -> {
        Object source = actionEvent.getSource();
            handleCell((Cell) source);       
    };
    
    private class Cell extends JButton {
        private final int row;
        private final int col;
        private       int value;

        Cell(final int row, final int col,
             final ActionListener actionListener) {
            this.row = row;
            this.col = col;
            addActionListener(actionListener);
            setText("");      
        }

        int getValue() {
            return value;
        }

        void setValue(int value) {
            this.value = value;
        }

        boolean isAMine() {
            return value == MINE;
        }

        void reset() {
            setValue(0);
            setEnabled(true);
            setText("");
            
        }

        void reveal() {
            setEnabled(false);
            if(isAMine()) {
            	setText(" ");
            	setBackground(Color.RED);
            }else {
            	setText(String.valueOf(value));
            	setForeground(Color.GRAY);
            }
            
        }
        
        void updateNeighbourCount() {
            getNeighbours(reusableStorage);
            for (Cell neighbour : reusableStorage) {
                if (neighbour == null) {
                    break;
                }
                if (neighbour.isAMine()) {
                    value++;
                }
            }
        }

        void getNeighbours(final Cell[] container) {
            // Empty all elements first
            for (int i = 0; i < reusableStorage.length; i++) {
                reusableStorage[i] = null;
            }

            int index = 0;

            for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
                for (int colOffset = -1; colOffset <= 1; colOffset++) {
                    // Make sure that we don't count ourselves
                    if (rowOffset == 0 && colOffset == 0) {
                        continue;
                    }
                    int rowValue = row + rowOffset;
                    int colValue = col + colOffset;

                    if (rowValue < 0 || rowValue >= Row
                        || colValue < 0 || colValue >= Col) {
                        continue;
                    }

                    container[index++] = cells[rowValue][colValue];
                }
            }
        }
        

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Cell cell = (Cell) obj;
            return row == cell.row &&
                   col == cell.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
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

        
        initializeGrid();
       
        // right click - flag (RED)
        // 모든 버튼에 mouseListener 을 추가해줌
        
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
        		if(cells[row][col].isAMine() == true) {
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
    	JMenuItem ng = new JMenuItem("새 게임");
    	JMenuItem ls = new JMenuItem("레벨 선택");
    	JMenuItem sv = new JMenuItem("저장");
    	JMenuItem op = new JMenuItem("불러오기");
    	
    	JButton mineNum = new JButton("지뢰수 : " + realMineNum);
    	mineNum.setEnabled(false);
    	
    	gameMenu.add(ng);
    	gameMenu.add(ls);
    	fileMenu.add(sv);
    	fileMenu.add(op);
    	mb.add(gameMenu);
    	mb.add(fileMenu);
    	mb.add(mineNum);

 
    	// timer
    	
    	JTextField tf = new JTextField(8);
    	tf.setEditable(false);

    	
    	
    	Timer t = new Timer(1000, new ActionListener() {
    	    private int hours;
    	    private int minutes;
    	    private int seconds;
    	    private String hour;
    	    private String minute;
    	    private String second;
    	    
    	    public void actionPerformed(ActionEvent e) {

    	        NumberFormat formatter = new DecimalFormat("00");
    	        if (seconds == N) {
    	            seconds = 00;
    	            minutes++;
    	        }

    	        if (minutes == N) {
    	            minutes = 00;
    	            hours++;
    	        }
    	        hour = formatter.format(hours);
    	        minute = formatter.format(minutes);
    	        second = formatter.format(seconds);
    	        tf.setText(String.valueOf(hour + ":" + minute + ":" + second));
    	        seconds++;
    	        
    	        if(stop == 1) {
    	        	seconds--;
    	        }
    	    }
    	    
    	});
    	

        final JToggleButton b = new JToggleButton("start");
        b.addItemListener(new ItemListener() {

        	@Override
            public void itemStateChanged(ItemEvent e) {
            
                if (b.isSelected()) {
                    t.start();
                    b.setText("stop");
                } else {
                    t.stop();
                    b.setText("start");
                }

            }
        });
        
    	t.setInitialDelay(0);

    	mb.add(tf);
    	mb.add(b);
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
				
				try {
					File myObj = new File("C:\\Users\\Suemin\\Minesweeper_play.dat");
					if(myObj.createNewFile()) {
						System.out.println("Game saved: " + myObj.getName());
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
				
			}
		};
		op.addActionListener(open);

    }
    
    // new Window    
	class newWindow extends JFrame {
	    // 버튼이 눌러지면 만들어지는 새 창을 정의한 클래스
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
    
    private void initializeGrid() {
        Container grid = new Container();
        grid.setLayout(new GridLayout(Row, Col));

        for (int row = 0; row < Row; row++) {
            for (int col = 0; col < Col; col++) {
                cells[row][col] = new Cell(row, col, actionListener);
                grid.add(cells[row][col]);
                
                
            }
        }
        
        createMines();
        
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

    private void createMines() {
    	realMineNum = 0;
        resetAllCells();

        final int mineCount = Row;
        final Random random    = new Random();

        // Map all (row, col) pairs to unique integers
        Set<Integer> positions = new HashSet<>(Row * Col);
        for (int row = 0; row < Row; row++) {
            for (int col = 0; col < Col; col++) {
              positions.add(row * Row + col);
            }
          
        }
        for(int col = 0; col < Col; col++) {
        	for(int row = 0; row < Row; row++) {
        		positions.add(col * Col + row);
        	}
        }

        // Initialize mines
        for (int index = 0; index < mineCount; index++) {
            int choice = random.nextInt(positions.size());
            int row    = choice / Row;
            int col    = choice % Col;
            cells[row][col].setValue(MINE);
            positions.remove(choice);
        }

        // Initialize neighbour counts
        for (int row = 0; row < Row; row++) {
            for (int col = 0; col < Col; col++) {
                if (!cells[row][col].isAMine()) {
                    cells[row][col].updateNeighbourCount();
                }
            }
        }
    }
    

    
    private void handleCell(Cell cell) {
        if (cell.isAMine()) {
            cell.setForeground(Color.RED);
            cell.reveal();
            stop = 1;
            revealBoardAndDisplay("You clicked on a mine!");
            
            
        }
//여러칸이 열리는 기능        
//        if (cell.getValue() == 0) {
//            Set<Cell> positions = new HashSet<>();
//            positions.add(cell);
//            cascade(positions);
//        } 
        else {
            cell.reveal();
        }  
        
        checkForWin();    
    }
    

    private void revealBoardAndDisplay(String message) {
        for (int row = 0; row < Row; row++) {
            for (int col = 0; col < Col; col++) {

        	    if(cells[row][col].isAMine()) {
        	    	cells[row][col].reveal();
        	    }
                cells[row][col].setEnabled(true);

           }
        }

        JOptionPane.showMessageDialog(
                frame, message, "Game Over",
                JOptionPane.ERROR_MESSAGE
        );

       // createMines();    
        
        
             
    }

    private void cascade(Set<Cell> positionsToClear) {
        while (!positionsToClear.isEmpty()) {
            // Set does not have a clean way for retrieving
            // a single element. This is the best way I could think of.
            Cell cell = positionsToClear.iterator().next();
            positionsToClear.remove(cell);
            cell.reveal();

            cell.getNeighbours(reusableStorage);
            for (Cell neighbour : reusableStorage) {
                if (neighbour == null) {
                    break;
                }
                if (neighbour.getValue() == 0
                    && neighbour.isEnabled()) {
                    positionsToClear.add(neighbour);
                } else {
                    neighbour.reveal();
                }
            }
        }
    }

    private void checkForWin() {
        boolean won = true;
        outer:
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                if (!cell.isAMine() && cell.isEnabled()) {
                    won = false;
                    break outer;
                }
            }
        }

        if (won) {
            JOptionPane.showMessageDialog(
                    frame, "You have won!", "Congratulations",
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