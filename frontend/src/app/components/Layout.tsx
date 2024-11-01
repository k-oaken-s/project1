import React from 'react';

const Layout: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    return (
        <div className="flex flex-col min-h-screen">
            <header className="bg-blue-600 text-white p-4">
                <h1 className="text-lg font-semibold">Your App Name</h1>
            </header>
            <main className="flex-grow container mx-auto p-6">
                {children}
            </main>
            <footer className="bg-gray-800 text-white text-center p-4">
                © 2024 Your Company
            </footer>
        </div>
    );
};

export default Layout;
