import { Component, OnInit } from '@angular/core';
import { Observable, map, tap } from 'rxjs';
import { Listing } from '../models';
import { ActivatedRoute, Router } from '@angular/router';
import { ListingService } from '../listing.service';

@Component({
  selector: 'app-listing',
  templateUrl: './listing.component.html',
  styleUrls: ['./listing.component.css']
})
export class ListingComponent implements OnInit{

  listing$!: Observable<Listing> 
  imageKey = ''
  ownerEmail: string = ''
  currentUserEmail: string = ''
  listingName: string = ''
  
  constructor(
    private activatedRoute: ActivatedRoute,
    private listingService: ListingService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.imageKey = this.activatedRoute.snapshot.params['imageKey']
    this.listing$ = this.listingService.getListingByKey(this.imageKey)

    this.listing$.subscribe(
      listing => {
        if (listing) {
          this.ownerEmail = listing.email
          this.listingName = listing.listingName
        }
      }
    )
  }

  isOwner(): boolean {
    const userJsonString = localStorage.getItem('user')
    if (userJsonString) {
      const userData = JSON.parse(userJsonString)
      this.currentUserEmail = userData.email
    }

    return this.ownerEmail === this.currentUserEmail
  }

  deleteListing(imageKey: string): void {
    this.listingService.deleteListing(imageKey)
                        .subscribe({
                          next: (successfulDelete) => {
                            alert("Listing deleted.");

                            // need to work on the return URL 
                            const returnUrl = this.activatedRoute.snapshot.queryParams['returnUrl'] || '/find';
                            this.router.navigateByUrl(returnUrl);
                          }
                        })
  }

  requestListing() {

    this.listingService.requestListing(this.ownerEmail, this.listingName, this.currentUserEmail)
      .subscribe({
        next: (response) => {
          alert("Request succesfully sent to listing owner.");
        },
        error: (error) => {
          alert(error);
        }
      });
  }
}
