
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GoMoku extends JPanel {

	public static void main(String[] args) {
		JFrame window = new JFrame("오목 V1.0");
		GoMoku content = new GoMoku();
		window.setContentPane(content);
		window.pack();
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation((screensize.width - window.getWidth()) / 2, (screensize.height - window.getHeight()) / 2);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);
	}

	private JButton newGameButton;
	private JButton resignButton;
	private JLabel message;
	private Image boardImage;
	private Image blackStone;
	private Image whiteStone;

	private int board_Width;
	private int board_Height;
	
	private GoMoKuManager gomokuManager;

	public GoMoku() {

		gomokuManager = new GoMoKuManager();
		
		setLayout(null);

		setPreferredSize(new Dimension(900, 800));

		setBackground(new Color(200, 213, 255));

		Board board = new Board();

		try {
			File boardImgFile = new File("board.png");
			boardImage = ImageIO.read(boardImgFile);
			File blackStoneImgFile = new File("blackStone.png");
			blackStone = ImageIO.read(blackStoneImgFile);
			File whiteStoneImgFile = new File("whiteStone.png");
			whiteStone = ImageIO.read(whiteStoneImgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		board_Width = boardImage.getWidth(null);
		board_Height = boardImage.getHeight(null);

		add(board);
		add(newGameButton);
		add(resignButton);
		add(message);

		board.setBounds(16, 16, 633, 633);
		newGameButton.setBounds(700, 60, 120, 30);
		resignButton.setBounds(700, 120, 120, 30);
		message.setBounds(0, 680, 700, 30);
	}

	class Board extends JPanel implements ActionListener, MouseListener {
;

		public Board() {
			// setBackground(new Color(241,129,11));
			addMouseListener(this);
			resignButton = new JButton("포기");
			resignButton.addActionListener(this);
			newGameButton = new JButton("새 게임");
			newGameButton.addActionListener(this);
			message = new JLabel("", JLabel.CENTER);
			message.setFont(new Font("Serif", Font.BOLD, 28));
			message.setForeground(Color.BLACK);
			doNewGame();
		}

		public void actionPerformed(ActionEvent evt) {
			Object src = evt.getSource();
			if (src == newGameButton)
				doNewGame();
			else if (src == resignButton)
				doResign();
		}

		void doNewGame() {
			if (gomokuManager.isGameInProgress() == true) {
				message.setText("현재 게임을 먼저 끝내세요");
				return;
			}
			
			gomokuManager.initBoard();
			
			gomokuManager.setCurrentPlayer(gomokuManager.getBLACK());
			
			message.setText("흑의 차례");
			gomokuManager.setGameInProgress(true);
			newGameButton.setEnabled(false);
			resignButton.setEnabled(true);
			gomokuManager.setWin_r1(-1);
			repaint();
		}

		void doResign() {
			if (gomokuManager.isGameInProgress() == false) {

				message.setText("진행중인 게임이 없습니다.");
				return;
			}
			if (gomokuManager.getCurrentPlayer() == gomokuManager.getWHITE())
				message.setText("백 기권. 흑의 승리");
			else
				message.setText("흑 기권 백의 승리.");
			newGameButton.setEnabled(true);
			resignButton.setEnabled(false);
			gomokuManager.setGameInProgress(false);
		}

		void gameOver(String str) {
			message.setText(str);
			newGameButton.setEnabled(true);
			resignButton.setEnabled(false);
			gomokuManager.setGameInProgress(false);
		}

		void doClickSquare(int row, int col) {
			if (gomokuManager.getBoardState(row, col) != gomokuManager.getEMPTY()) {
				if (gomokuManager.getCurrentPlayer() == gomokuManager.getBLACK())
					message.setText("흑 : 빈 칸에 돌을 놔주세요.");
				else
					message.setText("백 : 빈 칸에 돌을 놔주세요.");
				return;
			}

			gomokuManager.setBoardState(row, col, gomokuManager.getCurrentPlayer());

			if (gomokuManager.winner(row, col)) {
				if (gomokuManager.getCurrentPlayer() == gomokuManager.getWHITE())
					gameOver("백의 승리");
				else
					gameOver("흑의 승리");
				repaint();
				return;
			}

			if (gomokuManager.isThreeThree(row, col)) {
				message.setText("삼삼입니다. 다른곳에 돌을 놓으세요.");
				gomokuManager.setBoardState(row, col, gomokuManager.getEMPTY());
				return;
			}
			
			if(gomokuManager.isFourFour(row,col)){
				message.setText("사사입니다. 다른곳에 돌을 놓으세요.");
				gomokuManager.setBoardState(row, col, gomokuManager.getEMPTY());
				return;
			}

			repaint();

			boolean emptySpace = false;
			for (int i = 0; i < gomokuManager.getROW(); i++)
				for (int j = 0; j < gomokuManager.getCOL(); j++)
					if (gomokuManager.getBoardState(i, j) == gomokuManager.getEMPTY())
						emptySpace = true;
			if (emptySpace == false) {
				gameOver("비겼습니다.");
				return;
			}

			if (gomokuManager.getCurrentPlayer() == gomokuManager.getBLACK()) {
				gomokuManager.setCurrentPlayer(gomokuManager.getWHITE());
				message.setText("백의 차례");
			} else {
				gomokuManager.setCurrentPlayer(gomokuManager.getBLACK());
				message.setText("흑의 차례");
			}
		}


		

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			// g.setColor(Color.DARK_GRAY);
			g.drawImage(boardImage, 0, 0, board_Width, board_Height, null);
			/*
			 * for (int i = 1; i < 15; i++) { g.drawLine(1 + 28*i, 0, 1 + 28*i,
			 * getSize().height); g.drawLine(0, 1 + 28*i, getSize().width, 1 +
			 * 28*i); } g.setColor(Color.BLACK);
			 * g.drawRect(0,0,getSize().width-1,getSize().height-1);
			 * g.drawRect(1,1,getSize().width-3,getSize().height-3);
			 * 
			 */

			for (int row = 0; row < 15; row++)
				for (int col = 0; col < 15; col++)
					if (gomokuManager.getBoardState(row, col) != gomokuManager.getEMPTY())
						drawPiece(g, gomokuManager.getBoardState(row, col), row, col);

			if (gomokuManager.getWin_r1() >= 0)
				drawWinLine(g);

		}

		private void drawPiece(Graphics g, int piece, int row, int col) {
			if (piece == gomokuManager.getWHITE())
				g.drawImage(whiteStone, 16 + 40 * col, 16 + 40 * row, whiteStone.getWidth(null),
						whiteStone.getHeight(null), null);
			else
				g.drawImage(blackStone, 16 + 40 * col, 16 + 40 * row, blackStone.getWidth(null),
						blackStone.getHeight(null), null);
		}

		private void drawWinLine(Graphics g) {
			g.setColor(Color.RED);
			g.drawLine(36 + 40 * gomokuManager.getWin_c1(), 36 + 40 * gomokuManager.getWin_r1(), 36 + 40 * gomokuManager.getWin_c2(), 36 + 40 * gomokuManager.getWin_r2());
			if (gomokuManager.getWin_r1() == gomokuManager.getWin_r2())
				g.drawLine(36 + 40 * gomokuManager.getWin_c1(), 35 + 40 * gomokuManager.getWin_r1(), 36 + 40 * gomokuManager.getWin_c2(), 35 + 40 * gomokuManager.getWin_r2());
			else
				g.drawLine(35 + 40 * gomokuManager.getWin_c1(), 36 + 40 * gomokuManager.getWin_r1(), 35 + 40 * gomokuManager.getWin_c2(), 36 + 40 * gomokuManager.getWin_r2());
		}

		public void mousePressed(MouseEvent evt) {
			if (gomokuManager.isGameInProgress() == false)
				message.setText("'새 게임' 버튼을 눌러주세요");
			else {
				int col = (evt.getX() - 16) / 40;
				int row = (evt.getY() - 16) / 40;
				if (col >= 0 && col < gomokuManager.getCOL() && row >= 0 && row < gomokuManager.getROW())
					doClickSquare(row, col);
			}
		}

		public void mouseReleased(MouseEvent evt) {
		}

		public void mouseClicked(MouseEvent evt) {
		}

		public void mouseEntered(MouseEvent evt) {
		}

		public void mouseExited(MouseEvent evt) {
		}
	}
}