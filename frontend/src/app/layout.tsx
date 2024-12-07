"use client";

import Link from "next/link";
import {useState} from "react";
import {AppstoreOutlined, HomeOutlined, LikeOutlined, UnorderedListOutlined,} from "@ant-design/icons";
import "tailwindcss/tailwind.css";
import "./globals.css";

export default function RootLayout({children}: { children: React.ReactNode }) {
    const [selectedKey, setSelectedKey] = useState("1");

    const handleMenuClick = (key: string) => {
        setSelectedKey(key);
    };

    return (
        <html lang="en" className="h-full">
        <body className="bg-[#1f1f1f] text-white min-h-screen flex flex-col">
        {/* ヘッダー */}
        <header className="bg-gradient-to-r from-[#4b278f] to-[#28508f] shadow-lg">
            <div className="container mx-auto flex justify-between items-center p-4">
                <h1
                    className="text-2xl font-bold cursor-pointer hover:text-yellow-300 transition duration-200"
                    onClick={() => setSelectedKey("1")}
                >
                    Rankify Hub
                </h1>
                <nav className="flex space-x-4">
                    <Link
                        href="/"
                        onClick={() => handleMenuClick("1")}
                        className={`flex items-center space-x-2 px-4 py-2 rounded-md ${
                            selectedKey === "1"
                                ? "bg-yellow-300 text-black"
                                : "hover:text-yellow-300"
                        } transition duration-200`}
                    >
                        <HomeOutlined/>
                        <span>ホーム</span>
                    </Link>
                    <Link
                        href="/categories"
                        onClick={() => handleMenuClick("2")}
                        className={`flex items-center space-x-2 px-4 py-2 rounded-md ${
                            selectedKey === "2"
                                ? "bg-yellow-300 text-black"
                                : "hover:text-yellow-300"
                        } transition duration-200`}
                    >
                        <AppstoreOutlined/>
                        <span>カテゴリー一覧</span>
                    </Link>
                    <Link
                        href="/latest"
                        onClick={() => handleMenuClick("3")}
                        className={`flex items-center space-x-2 px-4 py-2 rounded-md ${
                            selectedKey === "3"
                                ? "bg-yellow-300 text-black"
                                : "hover:text-yellow-300"
                        } transition duration-200`}
                    >
                        <UnorderedListOutlined/>
                        <span>最新順</span>
                    </Link>
                    <Link
                        href="/popular"
                        onClick={() => handleMenuClick("4")}
                        className={`flex items-center space-x-2 px-4 py-2 rounded-md ${
                            selectedKey === "4"
                                ? "bg-yellow-300 text-black"
                                : "hover:text-yellow-300"
                        } transition duration-200`}
                    >
                        <LikeOutlined/>
                        <span>イイね順</span>
                    </Link>
                </nav>
            </div>
        </header>

        {/* メインコンテンツ */}
        <main className="flex-grow container mx-auto p-6 bg-[#1f1f1f] text-gray-300">
            {children}
        </main>

        {/* フッター */}
        <footer className="bg-[#141414] text-gray-300 text-center py-4">
            <p className="text-sm">©2024 Rankify Hub. All Rights Reserved.</p>
        </footer>
        </body>
        </html>
    );
}
