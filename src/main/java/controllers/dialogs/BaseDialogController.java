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

}
