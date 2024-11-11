import { Layout, Typography } from 'antd';
import 'antd/dist/reset.css';
import './globals.css';

const { Header, Content } = Layout;

export default function RootLayout({
    children,
}: {
    children: React.ReactNode;
}) {
    return (
        <html lang="en">
            <body>
                <Layout>
                    <Header>
                        <Typography.Title level={2} style={{ color: '#fff', margin: 0 }}>
                            Rankify Hub
                        </Typography.Title>
                    </Header>
                    <Content style={{ padding: '24px' }}>
                        {children}
                    </Content>
                </Layout>
            </body>
        </html>
    );
}
