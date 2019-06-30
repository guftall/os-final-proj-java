import java.util.ArrayList;
import java.util.List;

public class LRU {
    private List<Frame> memory = new ArrayList<>();

    private int frameCount;
    private String pages;

    private int currentPageSequenceIndex = 0;

    public LRU(int frameCount, String pages) {
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

            return emptyFrame();
        }

        List<Frame> list = new ArrayList();
        list.addAll(memory);

        Frame f = null;
        for (int i=currentPageSequenceIndex - 1; i>0; i--) {

            for (int j=0; j<list.size(); j++) {

                Frame tmpFrame = list.get(j);
                int r = Integer.parseInt(String.valueOf(pages.charAt(i)));
                if (tmpFrame.p.number == r) {
                    list.remove(j);
                    break;
                }
            }

            if (list.size() == 1 || i == pages.length() - 1) {
                f = list.get(0);
                break;
            }
        }

        Frame result = new Frame();
        result.number = f.number;
        result.p = new Page();
        result.p.number = f.p.number;

        f.number = Integer.parseInt(String.valueOf(pages.charAt(currentPageSequenceIndex)));

        currentPageSequenceIndex =currentPageSequenceIndex + 1;
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
