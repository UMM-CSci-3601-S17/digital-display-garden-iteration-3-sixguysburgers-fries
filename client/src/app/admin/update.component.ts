import {Component, OnInit, ViewChild} from '@angular/core';
import { AdminService } from './admin.service';
//import { NguiMessagePopupComponent, NguiPopupComponent} from '@ngui/popup';


@Component({
    selector: 'update-component',
    templateUrl: 'update.component.html',
})

export class UpdateComponent implements OnInit {

    @ViewChild('fu') fu;

    filename:string;
    uploadAttempted:boolean = false;

    handleUpload(){
        this.fu.update().subscribe(
            response => {
                this.filename = response.json();
                this.uploadAttempted = true;
            },
            err => {
                this.uploadAttempted = true;
            }

        );
    }

    fName:string;
    clearAttempted;boolean = false;

    clearDb() {
        this.fu.clear().subscribe(
            response => {
                this.fName = response.json();
                this.clearAttempted = true;
            },
            err => {
                this.clearAttempted = true;
            }
        );
    }




    // // https://github.com/ng2-ui/popup/blob/master/app/app.component.ts
    // @ViewChild(NguiPopupComponent) popup: NguiPopupComponent;
    // message: string;
    //
    // constructor() {}
    //
    // openPopup(size, title) {
    //     //noinspection TypeScriptUnresolvedFunction
    //     this.popup.open(NguiMessagePopupComponent, {
    //         classNames: size,
    //         title: title,
    //         message: "This is message given using popup.open()",
    //         buttons: {
    //             OK: () => {
    //                 this.message = "Ok button is pressed";
    //             },
    //             CANCEL: () => {
    //                 this.message = "Cancel button is pressed";
    //                 //noinspection TypeScriptUnresolvedFunction
    //                 this.popup.close();
    //             }
    //         }
    //     });
    // }
    //
    // openCustomPopup() {
    //     //noinspection TypeScriptUnresolvedFunction
    //     this.popup.open(CustomPopupComponent, {
    //         classNames: 'custom',
    //         closeButton: false,
    //     });
    // }


    ngOnInit(): void {


    }
}
