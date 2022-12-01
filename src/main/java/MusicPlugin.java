import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.IOException;

public class MusicPlugin extends AnAction {

    private final String CALENDAR = "C:\\Users\\HP\\OneDrive\\Desktop\\labs\\sem5\\dev\\lab-5\\src\\main\\resources\\Calendar.au";

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        try {
            var sound = new Sound(new FileInputStream(CALENDAR).readAllBytes(), 5);
            sound.play();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean isDumbAware() {
        return false;
    }
}
