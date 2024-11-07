import Link from 'next/link';

const Header = () => {
    return (
        <header className="bg-blue-600 text-white shadow-md py-4 px-6">
            <div className="container mx-auto flex justify-between items-center">
                <h1 className="text-xl font-bold">
                    <Link href="/" className="hover:text-blue-300">TierMaker</Link>
                </h1>
                <nav>
                    <ul className="flex space-x-6">
                        <li>
                            <Link href="/about" className="hover:text-blue-300">About</Link>
                        </li>
                        <li>
                            <Link href="/categories" className="hover:text-blue-300">Categories</Link>
                        </li>
                        <li>
                            <Link href="/contact" className="hover:text-blue-300">Contact</Link>
                        </li>
                    </ul>
                </nav>
            </div>
        </header>
    );
};

export default Header;
