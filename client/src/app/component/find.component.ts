import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ListingService } from '../listing.service';
import { Listing } from '../models';
import { Observable, of, switchMap } from 'rxjs';

@Component({
  selector: 'app-find',
  templateUrl: './find.component.html',
  styleUrls: ['./find.component.css']
})
export class FindComponent implements OnInit {

  listings$!: Observable<Listing[] | null> 
  filteredListings$!: Observable<Listing[] | null> 
  search!: string

  constructor(
    private activatedRoute: ActivatedRoute,
    private listingService: ListingService
  ) {}

  ngOnInit(): void { 

    this.listings$ = this.activatedRoute.queryParams.pipe(
      switchMap((params) => {
        this.search = params['search'] || ''; 
        if (!this.search) {
          return this.listingService.getListings();

        } else {
          // todo: if search returns null, show placeholder
          return this.listingService.getListingsByKeyword(this.search);
        }
      })
    );
  }

  // todo: fix the keyup search
  // onSearch($event: any): void {
  //   const searchText = $event.target.value
  //   this.filteredListings$ = this.listings$.pipe(
  //     map((listings: Listing[] | null) => {
  //       if (!searchText || searchText.trim() === '') {
  //         return null
  //       } else if (listings != null) {
  //         return listings.filter(listing => 
  //           listing.listingName.toLowerCase().includes(searchText.toLowerCase())
  //         );
  //       } else {
  //         return null
  //       }
  //     })
  //   );
  // }
}
