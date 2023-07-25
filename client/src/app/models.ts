export interface User {
    username: string
    password: string
    password2: string
    firstName: string
    lastName: string
    email: string
    phone: string
    country: string
    postalCode: string 
}

export interface EmailLogin {
    email: string
    password: string
}

export interface Listing {
    key: string
    listingName: string
    description: string
    email: string
    url: string
}

export interface RequestListing {
    listingName: string
    ownerEmail: string
    currentUserEmail: string
}

export interface DonationData {
    name: string
    amount: string
}