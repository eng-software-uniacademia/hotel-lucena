function handleSubmit(xhr, status, args, dialog) {
    const jqDialog = jQuery('#'+dialog.id);
    if(args.validationFailed) {
        jqDialog.effect('shake', { times:3 }, 100);
    } else {
        dialog.hide();
    }
}
