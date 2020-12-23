package net.clementlevallois.javafxtest.gui;

import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.ServiceLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.clementlevallois.javafxtest.logic.ReturnText;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var vBox = new VBox(label);

        Path pluginsFolderPath = Paths.get("mods").toFile().listFiles((dir, name) -> name.equals("plugins"))[0].toPath();
        System.out.println(pluginsFolderPath.toAbsolutePath().toString());

        DirectoryStream<Path> pluginDirectoriesAsPath = Files.newDirectoryStream(pluginsFolderPath, entry -> Files.isDirectory(entry) & entry.getFileName().toString().contains("plugin"));
        Iterator<Path> pluginDirectoriesIterator = pluginDirectoriesAsPath.iterator();

        while (pluginDirectoriesIterator.hasNext()) {
            Path nextPluginDirectory = pluginDirectoriesIterator.next();
            System.out.println("current plugin dir: " + nextPluginDirectory.toString());

            ModuleLayer layer = createLayerForOnePlugin(nextPluginDirectory);

            ServiceLoader<ReturnText> serviceLoader = ServiceLoader.load(layer, ReturnText.class);
            Optional<ReturnText> findFirst = serviceLoader.findFirst();
            boolean present = findFirst.isPresent();
            if (!present) {
                System.out.println("no plugin present in directory " + nextPluginDirectory.getFileName().toString() + ". Aborting");
                continue;
            }
            ReturnText plugin = findFirst.get();
            var labelPlugin = new Label(plugin.comment());
            vBox.getChildren().add(labelPlugin);

        }
        var scene = new Scene(vBox, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


    static ModuleLayer createLayerForOnePlugin(Path pluginPath) {
        Configuration configuration = createConfiguration(pluginPath);
        ClassLoader thisLoader = getThisLoader();
        return getThisLayer()
                .defineModulesWithOneLoader(configuration, thisLoader);

    }

    private static Configuration createConfiguration(Path modulePaths) {
        return getThisLayer()
                .configuration()
                .resolveAndBind(
                        ModuleFinder.of(),
                        ModuleFinder.of(modulePaths),
                        Collections.emptyList());
    }

    private static ModuleLayer getThisLayer() {
        return App.class.getModule().getLayer();
    }

    private static ClassLoader getThisLoader() {
        return App.class.getClassLoader();
    }
}
