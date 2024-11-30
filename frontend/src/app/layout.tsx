"use client";

import {Layout, Menu, Typography} from 'antd';
import {AppstoreOutlined, HomeOutlined, LikeOutlined, UnorderedListOutlined} from '@ant-design/icons';
import 'antd/dist/reset.css';
import 'tailwindcss/tailwind.css';
import './globals.css';
import {useState} from 'react';

const {Header, Content, Footer} = Layout;

export default function RootLayout({children}: { children: React.ReactNode }) {
    const [selectedKey, setSelectedKey] = useState('1');

    const handleTitleClick = () => {
        setSelectedKey('1');
    };

    return (
        <html>
        <body style={{backgroundColor: '#1f1f1f', color: '#fff'}}>
        <Layout style={{minHeight: '100%', backgroundColor: '#1f1f1f'}}>
            <Header style={{
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center',
                background: 'linear-gradient(135deg, #4b278f, #28508f)',
                padding: '0',
                boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
                backdropFilter: 'blur(10px)',
                animation: 'fadeIn 1s'
            }}>
                <Typography.Title
                    level={2}
                    style={{
                        color: '#fff',
                        margin: '0 20px',
                        fontFamily: 'Helvetica, Arial, sans-serif',
                        cursor: 'pointer'
                    }}
                    onClick={handleTitleClick}
                >
                    Rankify Hub
                </Typography.Title>
                <Menu
                    mode="horizontal"
                    theme="dark"
                    selectedKeys={[selectedKey]}
                    style={{flex: 1, borderBottom: 'none', backgroundColor: 'transparent'}}
                    onClick={e => setSelectedKey(e.key)}
                >
                    <Menu.Item key="1" icon={<HomeOutlined/>} style={{color: '#fff'}}
                               className="hover:text-yellow-300 transition duration-200">
                        ホーム
                    </Menu.Item>
                    <Menu.Item key="2" icon={<AppstoreOutlined/>} style={{color: '#fff'}}
                               className="hover:text-yellow-300 transition duration-200">
                        カテゴリー一覧
                    </Menu.Item>
                    <Menu.Item key="3" icon={<UnorderedListOutlined/>} style={{color: '#fff'}}
                               className="hover:text-yellow-300 transition duration-200">
                        最新順
                    </Menu.Item>
                    <Menu.Item key="4" icon={<LikeOutlined/>} style={{color: '#fff'}}
                               className="hover:text-yellow-300 transition duration-200">
                        イイね順
                    </Menu.Item>
                </Menu>
            </Header>
            <Content style={{padding: '24px', backgroundColor: '#1f1f1f', color: '#d3d3d3', flexGrow: 1}}>
                {children}
            </Content>
            <Footer style={{textAlign: 'center', backgroundColor: '#141414', color: '#d3d3d3'}}>
                ©2024 Rankify Hub. All Rights Reserved.
            </Footer>
        </Layout>
        </body>
        </html>
    );
}
