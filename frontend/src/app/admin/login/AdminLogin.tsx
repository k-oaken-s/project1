import {getApiBaseUrl} from '@/utils/getApiBaseUrl';
import axios from 'axios';
import {useRouter} from 'next/navigation';
import {useState} from 'react';
import {Button, Form, Input, Typography} from 'antd';

const {Title} = Typography;

const AdminLogin = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const router = useRouter();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const response = await axios.post(`${getApiBaseUrl()}/admin/login`, {
                username,
                password,
            });

            if (response.data.token) {
                localStorage.setItem('token', response.data.token);
                router.push('/admin');
            } else {
                alert('ログインに失敗しました');
            }
        } catch (error) {
            console.error(error);
            alert('ログインに失敗しました');
        }
    };

    return (
        <div style={{maxWidth: '400px', margin: 'auto', padding: '40px', textAlign: 'center', color: '#d3d3d3'}}>
            <Title level={2} style={{color: '#d3d3d3'}}>Admin Login</Title>
            <Form onSubmitCapture={handleLogin} layout="vertical">
                <Form.Item label={<span style={{color: '#d3d3d3'}}>Username</span>} required>
                    <Input
                        placeholder="Enter your username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        style={{color: '#d3d3d3', backgroundColor: '#333', borderColor: '#444'}}
                    />
                </Form.Item>
                <Form.Item label={<span style={{color: '#d3d3d3'}}>Password</span>} required>
                    <Input.Password
                        placeholder="Enter your password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        style={{color: '#d3d3d3', backgroundColor: '#333', borderColor: '#444'}}
                    />
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit"
                            style={{width: '100%', backgroundColor: '#1a73e8', borderColor: '#1a73e8'}}>
                        Login
                    </Button>
                </Form.Item>
            </Form>
        </div>
    );
};

export default AdminLogin;
