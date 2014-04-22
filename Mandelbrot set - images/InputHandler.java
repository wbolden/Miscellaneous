

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class InputHandler implements KeyListener, MouseListener {


	


	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch( keyCode ) 
		{ 
			case KeyEvent.VK_J:
				//

				break;
		}		
			
	}
	

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch( keyCode ) 
		{ 
		
			
		}	
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
//		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
