package GUI.Controller.MainController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ChartsDemoUIController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField monInput, tueInput, wedInput, thuInput, friInput, satInput, sunInput;

    @FXML
    private TextField productAInput, productBInput, productCInput, productDInput, productEInput;

    @FXML
    private TextField productAInputName, productBInputName, productCInputName, productDInputName, productEInputName;

    public VBox vBox;


    @FXML
    private ScatterChart<String, Number> chart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        borderPane.setCenter(buildBarChart());
    }

    @FXML
    private void handleShowBarChart(ActionEvent actionEvent) {
        borderPane.setCenter(buildBarChart());
    }

   @FXML
    private void handleShowLineChart(ActionEvent actionEvent) {
        borderPane.setCenter(buildLineChart());
    }
    @FXML
    private void handleShowAreaChart(ActionEvent actionEvent) {
        borderPane.setCenter(buildAreaChart());
    }

    private AreaChart<Number, Number> buildAreaChart() {
        // Create the X and Y axes
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Day");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Drinks sold");

        // Create the LineChart with the axes
        AreaChart<Number, Number> areaChart = new AreaChart<>(xAxis, yAxis);

        // Creating a series and adding data
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Drinks sold in January");

        Random r = new Random();
        for (int i = 0; i < 31; i++) {
            series.getData().add(new XYChart.Data<>(i+1,r.nextInt(350)+150));
        }

        areaChart.getData().add(series);

        return areaChart;
    }

    private LineChart<Number, Number> buildLineChart() {
        // Create the X and Y axes
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Day");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Drinks sold");

        // Create the LineChart with the axes
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        // Creating a series and adding data
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Drinks sold in January");

        Random r = new Random();
        for (int i = 0; i < 31; i++) {
            series.getData().add(new XYChart.Data<>(i+1,r.nextInt(350)+150));
        }

        lineChart.getData().add(series);

        return lineChart;
    }

    private ScatterChart<String, Number> buildScatterChart() {
        // Create the X and Y axes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Day");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Drinks sold");

        // Create the ScatterChart with the axes
        ScatterChart<String, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);

        // Creating a series and adding data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Drinks sold in January");

        series.getData().add(new XYChart.Data<>("Monday", getNumberFromTextField(monInput)));
        series.getData().add(new XYChart.Data<>("Tuesday", getNumberFromTextField(tueInput)));
        series.getData().add(new XYChart.Data<>("Wednesday", getNumberFromTextField(wedInput)));
        series.getData().add(new XYChart.Data<>("Thursday", getNumberFromTextField(thuInput)));
        series.getData().add(new XYChart.Data<>("Friday", getNumberFromTextField(friInput)));
        series.getData().add(new XYChart.Data<>("Saturday", getNumberFromTextField(satInput)));
        series.getData().add(new XYChart.Data<>("Sunday", getNumberFromTextField(sunInput)));

        scatterChart.getData().add(series);

        return scatterChart;
    }

    private Number getNumberFromTextField(TextField textField) {
        try {
            return Integer.parseInt(textField.getText());
        } catch (NumberFormatException e) {
            return 0; // Default value in case of parsing error
        }
    }


    /**
     * Builds a bar chart with static data
     */
    private BarChart buildBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Product");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("quantity sold");

        BarChart barChart = new BarChart(xAxis, yAxis);

        XYChart.Series data = new XYChart.Series();
        data.setName("Products Sold");

        // provide data
        data.getData().add(new XYChart.Data("Product A", 3000));
        data.getData().add(new XYChart.Data("Product B", 1500));
        data.getData().add(new XYChart.Data("Product C", 100));

        barChart.getData().add(data);
        barChart.setLegendVisible(false);


        // Create the context menu and menu item(s)
        ContextMenu contextMenu = new ContextMenu();
        MenuItem miSwitchToPieChart = new MenuItem("Switch to Pie Chart");
        contextMenu.getItems().add(miSwitchToPieChart);


        //Display context menu
        barChart.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            contextMenu.show(barChart, event.getScreenX(), event.getScreenY());
                        }
                    }
                });
        miSwitchToPieChart.setOnAction((ActionEvent actionEvent) -> {
            borderPane.setCenter(buildPieChart());
        });
        return barChart;
    }

    /**
     * Build pie chart with static data and return it
     *
     * @return
     */

    private String getStringFromTextField(TextField textField) {
        if (textField.getText().isEmpty()) {
            return "Unknown";
        } else {
            return textField.getText();
        }
    }

    private PieChart buildPieChart() {

        //Create Data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data(getStringFromTextField(productAInputName), getNumberFromTextField(productAInput).doubleValue()),
                new PieChart.Data(getStringFromTextField(productBInputName), getNumberFromTextField(productBInput).doubleValue()),
                new PieChart.Data(getStringFromTextField(productCInputName), getNumberFromTextField(productCInput).doubleValue()),
                new PieChart.Data(getStringFromTextField(productDInputName), getNumberFromTextField(productDInput).doubleValue()),
                new PieChart.Data(getStringFromTextField(productEInputName), getNumberFromTextField(productEInput).doubleValue())
        );


        //Create PieChart Object
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Products sold");
        pieChart.setClockwise(true);
        pieChart.setLabelLineLength(50);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(180);

        return pieChart;
    }

    @FXML
    private void handleShowPieChart(ActionEvent event) {
        VBox layout = new VBox(10); // VBox with spacing of 10
        layout.setAlignment(Pos.CENTER);
        Button updateButton = new Button("Update Pie Chart");
        updateButton.setOnAction(e -> handleRefreshPieChart());

        HBox productABox = new HBox(5, productAInput, productAInputName);
        HBox productBBox = new HBox(5, productBInput, productBInputName);
        HBox productCBox = new HBox(5, productCInput, productCInputName);
        HBox productDBox = new HBox(5, productDInput, productDInputName);
        HBox productEBox = new HBox(5, productEInput, productEInputName);

        layout.getChildren().addAll(buildPieChart(), productABox, productBBox, productCBox, productDBox, productEBox, updateButton);
        borderPane.setCenter(layout);
    }



    @FXML
    private void handleClose(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    private void handleUpdateData(ActionEvent actionEvent) {
        Node node = borderPane.getCenter();

        if (node instanceof PieChart) {
            PieChart pc = (PieChart) node;

            pc.getData().get(2).setPieValue(750);
        } else if (node instanceof BarChart) {
            BarChart<?, ?> bc = (BarChart) node;
            if (!bc.getData().isEmpty() && bc.getData().get(0).getData().size() > 2) {
                XYChart.Series series = (XYChart.Series) bc.getData().get(0);
                XYChart.Data data = (XYChart.Data) series.getData().get(2); // Access the third data point
                data.setYValue(750); // Update the value
            }
        }


    }


    @FXML
    private void handleShowScatterChart(ActionEvent actionEvent) {
        VBox layout = new VBox(10); // VBox with spacing of 10
        layout.setAlignment(Pos.CENTER);
        Button updateButton = new Button("Update Scatter Chart");
        updateButton.setOnAction(e -> handleRefreshScatterChart());

        layout.getChildren().addAll(buildScatterChart(), monInput, tueInput, wedInput, thuInput, friInput, satInput, sunInput, updateButton);

        borderPane.setCenter(layout);
    }


    @FXML
    private void handleRefreshScatterChart() {
        borderPane.setCenter(buildScatterChart());
    }

    @FXML
    private void handleRefreshPieChart() {
        borderPane.setCenter(buildPieChart());

    }
}
