import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//Christian Márquez
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaCarrera());
    }
}

class Hilo implements Runnable {
    private static int lugar = 1; // Ya no se reinicia cada vez
    private final JLabel personaje;
    private final JLabel labelFinal;
    private final String nombre;
    private final Thread t;

    public Hilo(String nombre, JLabel personaje, JLabel labelFinal) {
        this.nombre = nombre;
        this.personaje = personaje;
        this.labelFinal = labelFinal;
        t = new Thread(this, nombre);
        t.start();
    }

    @Override
    public void run() {
        try {
            int retardo = 10 + (int) (Math.random() * 20); // Más razonable
            labelFinal.setVisible(false);
            personaje.setVisible(true);

            for (int i = 50; i <= 400; i++) {
                personaje.setLocation(i, personaje.getY());
                Thread.sleep(retardo);
            }

            personaje.setVisible(false);
            int posicion;
            synchronized (Hilo.class) {
                posicion = lugar++;
            }
            labelFinal.setText(nombre + " ha llegado en la posición: " + posicion);
            labelFinal.setVisible(true);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class VentanaCarrera extends JFrame {
    public VentanaCarrera() {
        super("Carrera de Velocistas");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Superman
        JLabel Superman = crearLabelConImagen("src/Sups.gif", 50, 20);
        JLabel superman_pos = new JLabel("Superman listo");
        superman_pos.setBounds(120, 20, 350, 50);

        // Krypto
        JLabel Krypto = crearLabelConImagen("src/Krypto.gif", 50, 80);
        JLabel Krypto_pos = new JLabel("Krypto listo");
        Krypto_pos.setBounds(120, 80, 350, 50);

        // Flash
        JLabel Flash = crearLabelConImagen("src/Flash.gif", 50, 140);
        JLabel Flash_pos = new JLabel("Flash listo");
        Flash_pos.setBounds(120, 140, 350, 50);

        // Botón
        JButton botonInicioCarrera = new JButton("Inicio de la carrera del siglo");
        botonInicioCarrera.setBounds(180, 200, 220, 30);

        botonInicioCarrera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hilo tsuperman = new Hilo("Superman", Superman, superman_pos);
                Hilo tkrypto = new Hilo("Krypto", Krypto, Krypto_pos);
                Hilo tFlash = new Hilo("Flash", Flash, Flash_pos);
            }
        });

        // Añadir componentes
        panel.add(Superman);
        panel.add(superman_pos);
        panel.add(Krypto);
        panel.add(Krypto_pos);
        panel.add(Flash);
        panel.add(Flash_pos);
        panel.add(botonInicioCarrera);

        add(panel);
        setVisible(true);
    }

    private JLabel crearLabelConImagen(String ruta, int x, int y) {
        Image imagen = new ImageIcon(ruta).getImage();
        ImageIcon icono = new ImageIcon(imagen.getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        JLabel label = new JLabel(icono);
        label.setBounds(x, y, 50, 50);
        return label;
    }
}
