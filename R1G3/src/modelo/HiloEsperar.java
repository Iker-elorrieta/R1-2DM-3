package modelo;

import java.awt.Color;

import javax.swing.JButton;

public class HiloEsperar extends Thread {

	HiloRegresivo serie, descanso;
	HiloCronometro hiloEjercicio;
	JButton botonEmpezar;
	int contEjercicios, contSeries;
	Workout workout;
	Ejercicio ejercicioActivo;

	public void run() {
		serie.start();
		while (serie.isAlive()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		contSeries++;

		descanso.start();
		while (descanso.isAlive()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(ejercicioActivo.getSeries().size());
		System.out.println(contSeries);
		if (ejercicioActivo.getSeries().size() >= contSeries) {
			botonEmpezar.setBackground(new Color(0, 128, 0));
			botonEmpezar.setForeground(Color.WHITE);
			botonEmpezar.setText("Siguiente Serie");
		} else {
			hiloEjercicio.terminar();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hiloEjercicio.getCronometro()
					.setText(hiloEjercicio.getCronometro().getText().split(":")[0] + ": Ejercicio Terminado");
			contEjercicios++;
			botonEmpezar.setBackground(new Color(0, 128, 0));
			botonEmpezar.setForeground(Color.WHITE);
			if (workout.getEjercicios().size() > contEjercicios) {
				botonEmpezar.setText("Siguiente Ejercicio");

			} else {
				botonEmpezar.setText("Workout Terminado");
				botonEmpezar.setEnabled(false);
			}
		}

	}

	public HiloEsperar(HiloRegresivo serie, HiloRegresivo descanso, HiloCronometro hiloEjercicio, JButton botonEmpezar,
			int contEjercicios, Workout workout, Ejercicio ejercicioActivo, int contSeries) {

		this.serie = serie;
		this.descanso = descanso;
		this.hiloEjercicio = hiloEjercicio;
		this.botonEmpezar = botonEmpezar;
		this.contEjercicios = contEjercicios;
		this.workout = workout;
		this.ejercicioActivo = ejercicioActivo;
		this.contSeries = contSeries ;
	}

}
