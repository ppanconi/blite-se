/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unifi.dsi.blide.lang;

import org.openide.cookies.LineCookie;
import org.openide.loaders.DataObject;
import org.openide.text.Line;
import org.openide.windows.OutputEvent;
import org.openide.windows.OutputListener;

/**
 *
 * @author panks
 */
public class BliteOutputListener implements OutputListener {

    private DataObject dObj;
    private int pos;
    private int realLineNo;

    public BliteOutputListener(DataObject dObj, String errorMessage) {
        this.dObj = dObj;

        int[] lc = parseLineNColum(errorMessage);

        realLineNo = lc[0];
        pos = lc[1];
    }

    public BliteOutputListener(DataObject dObj, int pos, int realLineNo) {
        this.dObj = dObj;
        this.pos = pos;
        this.realLineNo = realLineNo;
    }

    public void outputLineSelected(OutputEvent ev) {
    }

    public void outputLineAction(OutputEvent ev) {

        if (realLineNo > 0) {
            LineCookie lc = dObj.getCookie(org.openide.cookies.LineCookie.class);
            Line line = lc.getLineSet().getOriginal(realLineNo - 1);
            if (pos > 0)
                line.show(Line.ShowOpenType.OPEN,  Line.ShowVisibilityType.FOCUS, pos - 1);
            else 
                line.show(Line.ShowOpenType.OPEN,  Line.ShowVisibilityType.FOCUS);
        }
    }

    public void outputLineCleared(OutputEvent ev) {
    }

    public static int[] parseLineNColum(String errMsg) {

        int l = -1, c = -1;

        String atLine = "at line ";
        int i = errMsg.indexOf(atLine);
        if (i > -1) {
            int j = errMsg.indexOf(',', i);

            String lineStr = errMsg.substring(i + atLine.length(), j);

            try {l = Integer.parseInt(lineStr);} catch (NumberFormatException ex) {}
        }

        String atColumn = "column ";
        i = errMsg.indexOf(atColumn);
        if (i > -1) {
            int j = errMsg.indexOf('.', i);

            String colStr = errMsg.substring(i + atColumn.length(), j);

            try {c = Integer.parseInt(colStr);} catch (NumberFormatException ex) {}
        }

        return new int[] {l, c};
    }

//    public static void main(String[] args) {
//        String errMsg = "Compiling file 'sample'... " +
//                "Compilation Error: Encountered \"::\" at line 12, column 5." +
//                "Was expecting:    \"{\" ...";
//
//        int[] lc = parseLineNColum(errMsg);
//
//        System.out.println("Line: " + lc[0] + " column: " + lc[1]);
//
//    }
}
