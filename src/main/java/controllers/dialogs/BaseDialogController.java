package controllers.dialogs;

import utils.AppDialog;

public abstract class BaseDialogController {

    // Dialog shows this scene
    protected AppDialog dialog;

    public AppDialog getDialog() {
        return dialog;
    }

    public void setDialog(AppDialog dialog) {
        this.dialog = dialog;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
        onSetParam();
    }

    public abstract void onSetParam();

    public  void onClose(){
       if(dialog!=null){
           dialog.close();
       }
    }

    Object param;

}
