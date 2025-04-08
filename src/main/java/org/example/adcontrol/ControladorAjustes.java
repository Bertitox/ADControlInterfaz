package org.example.adcontrol;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControladorAjustes {

    @FXML
    private MenuButton MenuButtonIdiomas;

    @FXML
    private CheckBox CheckBoxSonido;

    @FXML
    private ImageView imageViewSonido;

    private Image imagenSonidoEncendido;
    private Image imagenSonidoApagado;

    @FXML
    public void initialize() {
        CheckBoxSonido.setSelected(true);
        // Cargar imágenes desde resources (asegúrate de que están en src/main/resources/Imagenes/)
        imagenSonidoEncendido = new Image(getClass().getResourceAsStream("/org/example/adcontrol/Imagenes/volumenencendido.png"));
        imagenSonidoApagado = new Image(getClass().getResourceAsStream("/org/example/adcontrol/Imagenes/volumenapagado.png"));

        // Inicializar menú de idiomas
        for (MenuItem item : MenuButtonIdiomas.getItems()) {
            item.setOnAction(e -> MenuButtonIdiomas.setText(item.getText()));
        }

        // Cambiar imagen al activar/desactivar sonido
        CheckBoxSonido.setOnAction(e -> {
            if (CheckBoxSonido.isSelected()) {
                imageViewSonido.setImage(imagenSonidoEncendido);
            } else {
                imageViewSonido.setImage(imagenSonidoApagado);
            }
        });

        // Establecer imagen inicial
        imageViewSonido.setImage(CheckBoxSonido.isSelected() ? imagenSonidoEncendido : imagenSonidoApagado);
    }
}
