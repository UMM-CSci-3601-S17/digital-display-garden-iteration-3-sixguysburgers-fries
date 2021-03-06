//Credit to http://stackoverflow.com/questions/36352405/file-upload-with-angular2-to-rest-api/39862337#39862337
//Thanks Brother Woodrow!
import { Component, ElementRef, Input, ViewChild } from '@angular/core';
import { Http } from '@angular/http';

@Component({
    selector: 'image-upload',
    template: '<input #fileInput class="camera-launch" type="file" accept="image/*" capture="camera" id="camera" >'
})
export class ImageUploadComponent {
    @Input() multiple: boolean = false;
    @ViewChild('fileInput') inputEl: ElementRef;

    constructor(private http: Http) {}

    upload(fileName: String, flowerName: String) {
        console.log("this.inputEl = " + this.inputEl.nativeElement.files.item(0));
        let inputEl: HTMLInputElement = this.inputEl.nativeElement;
        let fileCount: number = inputEl.files.length;
        let formData = new FormData();
        if (fileCount > 0) { // a file was selected
            for (let i = 0; i < fileCount; i++) {
                formData.append('file[]', inputEl.files.item(i));
            }
            formData.append('name', fileName);
            formData.append('flower',flowerName);
            console.log("filename = " + fileName);
            return this.http.post(API_URL + "upload-photo", formData);
        }
    }
}