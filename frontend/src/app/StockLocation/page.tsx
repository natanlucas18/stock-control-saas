"use server"
import LocationsDataTable from '@/components/datatable-location/_components/location-datatable';
import Nav from '../Nav/page'
import { cookies } from 'next/headers';
import { getServerSession } from 'next-auth';
import { redirect } from 'next/navigation'


const accessToken = (await cookies()).get("accessToken")?.value;
async function fetchLocations() {
  const response = await fetch('http://localhost:8080/api/stock-locations', {
    headers: {
      'Authorization': `Bearer ${accessToken}`
    },
    next: {
      tags: ['stockLocation'],
      revalidate: 60
    }
  });
  return await response.json();
}


export default async function StockLocation() {
  const session = getServerSession();

  if (!session) {
    redirect('/Login');
  }

  const locations = await fetchLocations();
  return (
    <>
      <Nav />
      <div className='h-[91vh] px-8 py-6'>
        <LocationsDataTable locations={locations} />
      </div>
    </>
  )
}