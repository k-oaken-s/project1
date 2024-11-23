"use client";

import { useFetchCategoryWithItems } from '@/hooks/useFetchCategoryWithItems';
import { Item } from '@/types/Item';
import { getApiBaseUrl } from '@/utils/getApiBaseUrl';
import { getImageUrl } from '@/utils/getImageUrl';
import { ArrowLeftOutlined, UploadOutlined } from '@ant-design/icons';
import { Button, Card, Input, List, message, Spin, Typography, Upload } from 'antd';
import axios from 'axios';
import Image from 'next/image';
import { useParams, useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';
import ImageWrapper from "@/components/ImageWrapper";

const { Title, Text } = Typography;

const CategoryDetailPage = () => {
    const params = useParams();
    const router = useRouter();
    const categoryId = Array.isArray(params?.id) ? params.id[0] : params?.id;
    const { category, isLoading } = useFetchCategoryWithItems(categoryId);
    const [items, setItems] = useState<Item[]>([]);
    const [editingItem, setEditingItem] = useState<Item | null>(null);
    const [itemName, setItemName] = useState('');
    const [itemImage, setItemImage] = useState<File | null | "remove">(null);

    useEffect(() => {
        if (category) setItems(category.items);
    }, [category]);

    const handleAddOrUpdateItem = async () => {
        const token = localStorage.getItem('token');
        if (!token) {
            router.push('/admin/login');
            return;
        }

        const formData = new FormData();
        formData.append('item', new Blob([JSON.stringify({ name: itemName })], { type: 'application/json' }));

        if (itemImage && itemImage !== "remove") formData.append('file', itemImage);
        if (editingItem && itemImage === "remove") formData.append('removeImage', 'true');

        try {
            const url = editingItem
                ? `${getApiBaseUrl()}/categories/${categoryId}/items/${editingItem.id}`
                : `${getApiBaseUrl()}/categories/${categoryId}/items`;

            const method = editingItem ? 'put' : 'post';
            const res = await axios[method]<Item>(url, formData, {
                headers: { Authorization: `Bearer ${token}` },
            });
            setItems((prevItems) =>
                editingItem
                    ? prevItems.map((item) => (item.id === editingItem.id ? res.data : item))
                    : [...prevItems, res.data]
            );
            message.success(editingItem ? "アイテムが更新されました" : "アイテムが追加されました");
            setEditingItem(null);
            setItemName('');
            setItemImage(null);
        } catch (error) {
            console.error("Failed to add/update item:", error);
            message.error("アイテムの処理に失敗しました");
        }
    };

    const handleImageUpload = (file: File) => {
        setItemImage(file);
    };

    const startEditingItem = (item: Item) => {
        setEditingItem(item);
        setItemName(item.name);
        setItemImage(null);
    };

    return (
        <div className="p-8 max-w-4xl mx-auto">
            {isLoading ? (
                <div className="text-center my-10">
                    <Spin size="large" />
                </div>
            ) : (
                <>
                    <Button type="primary" onClick={() => router.push('/admin')} className="mb-6">
                        <ArrowLeftOutlined />
                        管理者ダッシュボードに戻る
                    </Button>

                    {category && (
                        <Card
                            className="mb-8"
                            title={<Title level={3}>{category.name}</Title>}
                            cover={
                                category.image ? (
                                    <ImageWrapper
                                        src={getImageUrl(category.image)}
                                        alt={`${category.name} image`}
                                        style={{ maxHeight: "300px", objectFit: "cover", width: "100%" }}
                                                                     width={400}
                                        height={400}
                                    />
                                ) : null
                            }
                        >
                            {category.description && <Text>{category.description}</Text>}
                        </Card>
                    )}

                    <Title level={4} className="mb-4">カテゴリーのアイテム一覧</Title>
                    <List
                        dataSource={items}
                        renderItem={(item) => (
                            <List.Item key={item.id} actions={[
                                <Button key={`edit-${item.id}`} onClick={() => startEditingItem(item)}>
                                    編集
                                </Button>
                            ]}>
                                <List.Item.Meta
                                    avatar={
                                        item.image ? (
                                            <ImageWrapper
                                                src={getImageUrl(item.image)}
                                                alt={`${item.name} image`}
                                                style={{ width: 50, height: 50, objectFit: "cover" }}
                                                                                             width={400}
                                                height={400}
                                            />
                                        ) : null
                                    }
                                    title={item.name}
                                />
                            </List.Item>
                        )}
                    />

                    <Card title={editingItem ? "アイテムを編集" : "新しいアイテムを追加"} className="mt-8">
                        <Input
                            placeholder="アイテム名を入力"
                            value={itemName}
                            onChange={(e) => setItemName(e.target.value)}
                            className="mb-3"
                        />
                        <Upload
                            beforeUpload={(file) => {
                                handleImageUpload(file);
                                return false; // アップロードをキャンセルしてプレビュー用に画像を設定
                            }}
                            onRemove={() => setItemImage("remove")}
                        >
                            <Button icon={<UploadOutlined />}>画像を選択</Button>
                        </Upload>
                        <Button
                            type="primary"
                            onClick={handleAddOrUpdateItem}
                            className="mt-4"
                        >
                            {editingItem ? "保存" : "追加"}
                        </Button>
                    </Card>
                </>
            )}
        </div>
    );
};

export default CategoryDetailPage;
