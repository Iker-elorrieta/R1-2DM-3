package modelo;

import javax.swing.JLabel;

public class HiloCronometro extends Thread {
	JLabel cronometro;
	int minutos;
	int segundos;
	boolean terminar = false;
	boolean parar = false;

	public HiloCronometro(JLabel cronometro) {
		// TODO Auto-generated constructor stub
		this.cronometro = cronometro;
	}

	public void run() {
		while (!terminar) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while (parar) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			segundos++;
			if (segundos == 60) {
				minutos++;
				segundos = 0;
			}
			cronometro.setText(minutos + ":" + segundos);
		}
	}

	public void terminar() {
		terminar = true;
	}

	public void cambiarEstado() {
		if (parar) {

			parar = false;
		} else {

			parar = true;
		}

	}
}
