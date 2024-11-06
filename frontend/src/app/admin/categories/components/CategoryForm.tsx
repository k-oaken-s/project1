import { useState } from 'react';

interface CategoryFormProps {
    onAddCategory: (name: string, image: File | null) => void;
}

const CategoryForm = ({ onAddCategory }: CategoryFormProps) => {
    const [newCategoryName, setNewCategoryName] = useState('');
    const [categoryImage, setCategoryImage] = useState<File | null>(null);

    const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files && e.target.files[0]) {
            setCategoryImage(e.target.files[0]);
        }
    };

    const handleSubmit = () => {
        onAddCategory(newCategoryName, categoryImage);
        setNewCategoryName('');
        setCategoryImage(null);
    };

    return (
        <div className="mb-6">
            <input
                type="text"
                placeholder="カテゴリー名を入力"
                value={newCategoryName}
                onChange={(e) => setNewCategoryName(e.target.value)}
                className="border rounded p-2 w-full mb-2"
            />
            <input type="file" onChange={handleImageUpload} className="mb-2" />
            <button onClick={handleSubmit} className="bg-green-500 text-white py-2 px-4 rounded hover:bg-green-600">
                カテゴリーを追加
            </button>
        </div>
    );
};

export default CategoryForm;
