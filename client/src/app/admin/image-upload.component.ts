//Credit to http://stackoverflow.com/questions/36352405/file-upload-with-angular2-to-rest-api/39862337#39862337
//Thanks Brother Woodrow!
import { Component, ElementRef, Input, ViewChild } from '@angular/core';
import { Http } from '@angular/http';

@Component({
    selector: 'image-upload',
    template: '<input type="image" [multiple]="multiple" #fileInput >'
})
export class ImageUploadComponent {
    @Input() multiple: boolean = false;
    @ViewChild('fileinput') inputEl: ElementRef;

    constructor(private http: Http) {}

    upload() {
        let inputEl: HTMLInputElement = this.inputEl.nativeElement;
        let fileCount: number = inputEl.files.length;
        let formData = new FormData();
        if (fileCount > 0) { // a file was selected
            for (let i = 0; i < fileCount; i++) {
                formData.append('file[]', inputEl.files.item(i));
            }
            return this.http.post(API_URL + "upload-photo", formData);
        }
    }
}