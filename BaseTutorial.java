/* Atenção, não altere este arquivo */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BaseTutorial extends Canvas {
	/** c&oacute;digo da tecla */
	int keyCode;
	/** caractere correspondente a tecla pressionada */
	char key;
	/** indica se uma tecla est&aacute; pressionada */
	boolean keyPressed;
	/** indica se um bot&atilde;o do mouse est&aacute; pressionado */
	boolean mousePressed = false;
	/** coordenada anterior do cursor mouse */
	int pmouseY, pmouseX;
	/** coordenada atual do cursor mouse */
	int mouseY, mouseX;
	/** dimens&otilde;es da &aacute;rea gr&aacute;fica */
	int width, height;

	Image offscreen = null;
	Graphics offgraphics = null;

	public BaseTutorial() {
		setSize(200, 200);

		Timer t = new Timer(33, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		t.setCoalesce(false);
		t.start();

		MouseAdapter mouseAdapter = new MouseAdapter() {

			void mouse(MouseEvent e) {
				pmouseX = mouseX;
				pmouseY = mouseY;
				mouseX = e.getX();
				mouseY = e.getY();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mouse(e);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				mouse(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				mouse(e);
				mousePressed = true;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				mouse(e);
				mousePressed = false;
			}
		};

		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
	}

	@Override
  public void setSize(int w, int h) {
    setPreferredSize(new Dimension(w, h));
    width = w;
    height = h;
  }

	@Override
	public void update(Graphics g) {
		if (height != getHeight() || width != getWidth() || offscreen == null) {
			height = getHeight();
			width = getWidth();
			offscreen = createImage(width, height);
			if (offgraphics != null) {
				offgraphics.dispose();
			}
			offgraphics = offscreen.getGraphics();
		}
		super.update(offgraphics);
		g.drawImage(offscreen, 0, 0, null);
	}

	/**
	 * Realiza a inicia&ccedil;&atilde;o do programa.
	 * <p>
	 * Esta fun&ccedil;&atilde;o pode ser usada para determinar as
	 * caracter&iacute;sticas iniciais do programa. Ela &eacute; executada
	 * apenas uma vez no in&iacute;cio da execu&ccedil;&atilde;o do programa. Se
	 * ela n&atilde;o for necess&aacute;ria, pode ser omitida.
	 */
	public void init() {
	}

	/**
	 * Realiza a atualiza&ccedil;&atilde;o da &aacute;rea gr&aacute;fica
	 * (canvas).
	 * <p>
	 * Esta fun&ccedil;&atilde;o &eacute; utilizada para atualizar o desenho
	 * apresentado na &aacute;rea gr&aacute;fica (canvas). Ela &eacute; chamada
	 * pelo sistema 30 vezes por segundo.
	 *
	 * @param g representa o contexto da área gráfica, utilizado para desenhar.
	 */
	public void paint(Graphics g){};

}

class Start extends JFrame {
	BaseTutorial canvas;

	public Start(BaseTutorial c) {
		canvas = c;
		add(canvas);
		setVisible(true);
		canvas.setSize(200, 200);
		canvas.init();
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		KeyListener keyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				canvas.keyPressed = true;
				canvas.key = e.getKeyChar();
				canvas.keyCode = e.getKeyCode();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				canvas.keyPressed = false;
				canvas.key = e.getKeyChar();
				canvas.keyCode = e.getKeyCode();
			}
		};
		addKeyListener(keyListener);
		canvas.addKeyListener(keyListener);
	}
}
