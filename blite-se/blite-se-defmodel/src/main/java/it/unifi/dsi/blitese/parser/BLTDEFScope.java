/* Generated By:JJTree: Do not edit this line. BLTDEFScope.java */

package it.unifi.dsi.blitese.parser;

public class BLTDEFScope extends AScope {
  public BLTDEFScope(int id) {
    super(id);
  }

  public BLTDEFScope(BliteParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(BliteParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

    
  
//added code start here ////////////////////////////////////////////////////////  
  
    public BLTDEFScope(BltDefBaseNode mainActivity,
                       BltDefBaseNode faultHandler,
                       BltDefBaseNode compensationHandler) {
        super(Integer.MIN_VALUE);
        
        setMainActivity(mainActivity);
        setFaultHandler(faultHandler);
        setCompensationHandler(compensationHandler);
    }
  
    @Override
    public String toString() {
        if (id == Integer.MIN_VALUE) return "Runtime Created Scope Definition";
        else return super.toString();
    }
}
