import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ListingService } from '../listing.service';

@Component({
  selector: 'app-upsert',
  templateUrl: './upsert.component.html',
  styleUrls: ['./upsert.component.css']
})
export class UpsertComponent implements OnInit {

  @ViewChild('uploadFile')
  uploadFile!: ElementRef

  form!: FormGroup;
  imageUrl!: string | ArrayBuffer | null;
  userEmail! : string;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private listingService: ListingService
  ) {}

  ngOnInit(): void {
    this.form = this.createListing()  
  }

  createListing(): FormGroup {
    return this.fb.group({
      file: this.fb.control<File | null>(null, [Validators.required]),
      listingName: this.fb.control<String>('Banana', [Validators.required, Validators.minLength(2)]),
      description: this.fb.control<String | null>(null)      
    })
  }

  onFileAdded(event: any): void {
    const file = event.target.files[0]
    if (file) {
      const reader = new FileReader()
      reader.onload = (e) => {
        this.imageUrl = e.target?.result as string | ArrayBuffer | null
      }
      reader.readAsDataURL(file)
    }
  }

  clearForm(): void {
    this.form.reset()
  }

  upload(): void {

    const f: File = this.uploadFile.nativeElement.files[0]
    const data = this.form.value

    const userJsonString = localStorage.getItem('user')
    if (userJsonString) {
      const userData = JSON.parse(userJsonString)
      this.userEmail = userData.email
    }

    this.listingService.upload(data['listingName'], data['description'], this.userEmail, f)
      .subscribe({
        next: (successfulUpload) => {
          alert("Listing upload success.")
          this.router.navigate(['/find/' + successfulUpload.id])
        },
        error: () => {
          alert("An error has occurred.")
        }
      })
  }
}