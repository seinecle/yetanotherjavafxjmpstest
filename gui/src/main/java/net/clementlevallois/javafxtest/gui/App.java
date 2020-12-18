package net.clementlevallois.javafxtest.gui;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.clementlevallois.javafxtest.logic.ReturnText;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();
       String[] args = {"C:\\Users\\levallois\\Google Drive\\open\\YetAnotherJavaFXjpmsTest\\mods"};

//        ModuleLayer layer = PluginUtils.createPluginLayer(args[0],"net.clementlevallois.mockobservedservice");
        ModuleLayer layer = createPluginLayer(args[0]);
        ServiceLoader<ReturnText> load = ServiceLoader.load(layer, ReturnText.class);
        Optional<ReturnText> findFirst1 = load.findFirst();
        boolean present = findFirst1.isPresent();
        if (!present){
            System.out.println("no plugin present. Aborting");
            System.exit(1);
        }
        ReturnText plugin = findFirst1.get();
            System.out.println("is alive? -> " + plugin.comment());
        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var label2 = new Label(plugin.comment());
        var flowPane = new FlowPane(label); 
        flowPane.getChildren().add(label2);
        var scene = new Scene(flowPane, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    
    static ModuleLayer createPluginLayer(String dir) {
        ModuleFinder finder = ModuleFinder.of(Paths.get(dir));
        Set<ModuleReference> pluginModuleRefs = finder.findAll();
        Set<String> pluginRoots = pluginModuleRefs.stream()
                .map(ref -> ref.descriptor().name())
                .filter(name -> name.startsWith("net.clementlevallois.javafxtest.plugin"))
                .collect(Collectors.toSet());
        System.out.println("found plugins: "+ pluginRoots.size());
//        System.out.println("root is: "+pluginRoots.iterator().next());
        ModuleLayer parent = ModuleLayer.boot();
        Configuration cf = parent.configuration()
                .resolve(finder, ModuleFinder.of(), pluginRoots);
        ClassLoader scl = ClassLoader.getSystemClassLoader();
        ModuleLayer layer = parent.defineModulesWithManyLoaders(cf, scl);
        return layer;
    }

}
