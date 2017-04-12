import {Component, OnInit, ViewChild} from '@angular/core';
import { AdminService } from './admin.service';
import { NguiMessagePopupComponent, NguiPopupComponent} from '@ngui/popup';


@Component({
    selector: 'import-component',
    templateUrl: 'import.component.html',
})

export class ImportComponent implements OnInit {

    okConfirmation:string = "no";

    @ViewChild('fu') fu;

    filename:string;
    uploadAttempted:boolean = false;

    handleUpload() {
        if (this.okConfirmation === "ok") {
            this.okConfirmation ="no";
            this.fu.upload().subscribe(
                response => {
                    this.filename = response.json();
                    this.uploadAttempted = true;
                },
                err => {
                    this.uploadAttempted = true;
                }
            );
        }
    }


    clearAttempted:string = "";

    clearDb() {
        if (this.okConfirmation === "deleteConfirm") {
            this.fu.clear().subscribe(
                response => {
                    this.clearAttempted = "good";
                },
                err => {
                    this.clearAttempted = "bad";
                }
            );
        }
        this.clearAttempted = "";
    }




    // https://github.com/ng2-ui/popup/blob/master/app/app.component.ts
    @ViewChild(NguiPopupComponent) popup: NguiPopupComponent;
    message: string;

    constructor() {}


    openPopup(size, title) {
        //noinspection TypeScriptUnresolvedFunction
        this.popup.open(NguiMessagePopupComponent, {
            classNames: size,
            title: title,
            message: "Are You Sure?",
            buttons: {
                OK: () => {
                    this.okConfirmation = "ok";
                    this.message = "Ok button is pressed";
                    this.popup.close();
                    this.handleUpload();
                },
                CANCEL: () => {
                    this.message = "Cancel button is pressed";
                    this.okConfirmation = "nope";
                    //noinspection TypeScriptUnresolvedFunction
                    this.popup.close();
                }
            }
        });
    }

    openPopup2(size, title) {
        //noinspection TypeScriptUnresolvedFunction
        this.popup.open(NguiMessagePopupComponent, {
            classNames: size,
            title: title,
            message: "Are You Sure?",
            buttons: {
                OK: () => {
                    this.okConfirmation = "deleteConfirm";
                    this.message = "Ok button is pressed";
                    this.popup.close();
                    this.clearDb();
                },
                CANCEL: () => {
                    this.message = "Cancel button is pressed";
                    this.okConfirmation = "nope";
                    //noinspection TypeScriptUnresolvedFunction
                    this.popup.close();
                }
            }
        });
    }

    ngOnInit(): void {

    }
}
