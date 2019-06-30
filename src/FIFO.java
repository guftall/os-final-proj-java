import java.util.ArrayList;
import java.util.List;

public class FIFO {
    private List<Frame> memory = new ArrayList<>();

    private List<Frame> queue = new ArrayList<>();
    int currentFrame = 0;

    private int frameCount;
    private String pages;

    private int currentPageSequenceIndex = 0;

    public FIFO(int frameCount, String pages) {
        for (int i=0; i<pages.length(); i++) {

            String pageStr = String.valueOf(pages.charAt(i));
            Frame f = new Frame();
            f.number = i;

            Page p = new Page();
            p.number = Integer.parseInt(pageStr);
            f.p = p;

            memory.add(f);
        }
        this.frameCount = frameCount;
        this.pages = pages;
    }

    public Frame getFrameToReplace() {

        if (!isFault()) {
            currentPageSequenceIndex = currentPageSequenceIndex + 1;
            return null;
        }

        if (memory.size() < this.frameCount) {

            Frame f = new Frame();
            f.number = currentFrame % frameCount;
            f.p = new Page();
            f.p.number = Integer.parseInt(String.valueOf(pages.charAt(currentPageSequenceIndex)));
            currentFrame++;
            return emptyFrame();
        }

        currentFrame++;
        Frame f = queue.remove(0);

        Frame push = new Frame();
        push.number = currentFrame % frameCount;
        push.p = new Page();
        push.p.number = Integer.parseInt(String.valueOf(pages.charAt(currentPageSequenceIndex)));

        currentFrame++;

        Frame result = new Frame();
        result.number = f.number;
        result.p = new Page();
        result.p.number = f.p.number;

        return result;
    }


    private Frame emptyFrame() {
        Frame f = new Frame();
        f.number = memory.size();
        f.p = new Page();
        f.p.number = Integer.parseInt(String.valueOf(pages.charAt(currentPageSequenceIndex - 1)));

        this.memory.add(f);
        return f;
    }

    private boolean isFault() {
        int s = Integer.parseInt(String.valueOf(pages.charAt(currentPageSequenceIndex)));
        for (int i=0; i<memory.size(); i++) {
            if (memory.get(i).p.number == s) {
                return false;
            }
        }

        return true;
    }
}
