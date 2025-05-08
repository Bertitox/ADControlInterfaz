package org.example.adcontrol;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.*;

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
    private Label labelVolumen;

    @FXML
    private Slider sliderVolumen;

    @FXML
    ColorPicker colorPicker;

    @FXML
    private Spinner<Integer> spinnerIntervalo;

    InfoInit infoInit = InfoInit.getInstance();


    @FXML
    public void initialize() throws IOException {
        cargarDatos();
        imagenSonidoApagado = new Image(getClass().getResourceAsStream("/org/example/adcontrol/Imagenes/volumenapagado.png"));
        imagenSonidoEncendido = new Image(getClass().getResourceAsStream("/org/example/adcontrol/Imagenes/volumenencendido.png"));

        for (MenuItem item : MenuButtonIdiomas.getItems()) {
            item.setOnAction(e -> {
                MenuButtonIdiomas.setText(item.getText());
                infoInit.setIdiomaLeido(MenuButtonIdiomas.getText());
            });
        }

        CheckBoxSonido.setOnAction(e -> {
            if (CheckBoxSonido.isSelected()) {
                imageViewSonido.setImage(imagenSonidoEncendido);
                infoInit.setMuteLeido(false);
                sliderVolumen.setDisable(false);
                labelVolumen.setDisable(false);
            } else {
                imageViewSonido.setImage(imagenSonidoApagado);
                infoInit.setMuteLeido(true);
                sliderVolumen.setDisable(true);
                labelVolumen.setDisable(true);
            }
        });
        imageViewSonido.setImage(CheckBoxSonido.isSelected() ? imagenSonidoEncendido : imagenSonidoApagado);

        sliderVolumen.setOnMouseReleased(e -> {
            if (CheckBoxSonido.isSelected()) {
                sliderVolumen.setDisable(false);
            } else {
                sliderVolumen.setDisable(true);
            }
            Double vol = sliderVolumen.getValue() * 0.01;
            infoInit.setVolumenLeido(vol);
        });

        colorPicker.setOnAction(e -> {
            Color color = colorPicker.getValue();
            String hex = String.format("#%02X%02X%02X",
                    (int) (color.getRed() * 255),
                    (int) (color.getGreen() * 255),
                    (int) (color.getBlue() * 255)
            );
            infoInit.setTemaLeido(hex);
        });

        // Configuración Spinner
        spinnerIntervalo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 120, infoInit.getIntervalo()));
        spinnerIntervalo.getValueFactory().valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                infoInit.setIntervalo(newValue);
            }
        });
    }

    public void cargarDatos() {
        MenuButtonIdiomas.setText(infoInit.getIdiomaLeido());

        CheckBoxSonido.setSelected(!infoInit.getMuteLeido());

        sliderVolumen.setValue(infoInit.getVolumenLeido() * 100.0);

        Color color = Color.web(infoInit.getTemaLeido());
        colorPicker.setValue(color);

        // Asignamos valor al Spinner desde infoInit
        if (spinnerIntervalo.getValueFactory() != null) {
            spinnerIntervalo.getValueFactory().setValue(infoInit.getIntervalo());
        }
    }

    public void restablecerAjustes() {
        infoInit.restablecerAjustes();
        cargarDatos();
    }
}