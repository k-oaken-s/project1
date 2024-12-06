"use client";

import {Layout} from 'antd';
import {AppstoreOutlined, HomeOutlined, LikeOutlined, UnorderedListOutlined} from '@ant-design/icons';
import 'antd/dist/reset.css';
import 'tailwindcss/tailwind.css';
import './globals.css';
import Link from 'next/link';
import {useState} from 'react';

const {Header, Content} = Layout;

export default function RootLayout({children}: { children: React.ReactNode }) {
    const [selectedKey, setSelectedKey] = useState('1');

    const handleMenuClick = (key: string) => {
        setSelectedKey(key);
    };

    return (
        <html>
        <body className="bg-[#1f1f1f] text-white min-h-screen flex flex-col">
        <Layout className="flex-1 flex flex-col bg-[#1f1f1f]">
            {/* Header */}
            <Header
                className="flex justify-between items-center bg-gradient-to-r from-[#4b278f] to-[#28508f] p-0 shadow-lg">
                <div className="flex items-center ml-5">
                    <h1
                        className="text-2xl font-bold text-white cursor-pointer hover:text-yellow-300 transition duration-200"
                        onClick={() => setSelectedKey('1')}
                    >
                        Rankify Hub
                    </h1>
                </div>
                <nav className="flex space-x-4 mr-5">
                    <button
                        onClick={() => handleMenuClick('1')}
                        className={`flex items-center space-x-2 px-4 py-2 rounded-md ${
                            selectedKey === '1' ? 'bg-yellow-300 text-black' : 'hover:text-yellow-300'
                        } transition duration-200`}
                    >
                        <HomeOutlined/>
                        <Link href="/">ホーム</Link>
                    </button>
                    <button
                        onClick={() => handleMenuClick('2')}
                        className={`flex items-center space-x-2 px-4 py-2 rounded-md ${
                            selectedKey === '2' ? 'bg-yellow-300 text-black' : 'hover:text-yellow-300'
                        } transition duration-200`}
                    >
                        <AppstoreOutlined/>
                        <Link href="/categories">カテゴリー一覧</Link>
                    </button>
                    <button
                        onClick={() => handleMenuClick('3')}
                        className={`flex items-center space-x-2 px-4 py-2 rounded-md ${
                            selectedKey === '3' ? 'bg-yellow-300 text-black' : 'hover:text-yellow-300'
                        } transition duration-200`}
                    >
                        <UnorderedListOutlined/>
                        <Link href="/latest">最新順</Link>
                    </button>
                    <button
                        onClick={() => handleMenuClick('4')}
                        className={`flex items-center space-x-2 px-4 py-2 rounded-md ${
                            selectedKey === '4' ? 'bg-yellow-300 text-black' : 'hover:text-yellow-300'
                        } transition duration-200`}
                    >
                        <LikeOutlined/>
                        <Link href="/popular">イイね順</Link>
                    </button>
                </nav>
            </Header>

            {/* Content */}
            <Content className="p-6 bg-[#1f1f1f] text-gray-300 flex-grow">
                {children}
            </Content>

            {/* Footer */}
            <footer className="bg-[#141414] text-gray-300 text-center py-4">
                <p className="text-sm">
                    ©2024 Rankify Hub. All Rights Reserved.
                </p>
            </footer>
        </Layout>
        </body>
        </html>
    );
}
