import { PathLinks } from '@/types/path-links';
import Link from 'next/link';

export default function HomePage() {
  return (
    <div>
      <h1>Home</h1>
      <Link href={PathLinks.SIGN_IN}>Login</Link>
    </div>
  );
}
