import { Category } from '@/types/Category';

type CategoryListProps = {
  categories: Category[];
  onDelete: (id: string) => void;
  onSelect: (id: string | null) => void;
  selectedCategoryId: string | null;
};

const CategoryList = ({ categories, onDelete, onSelect, selectedCategoryId }: CategoryListProps) => (
  <ul>
    {categories.length > 0 ? (
      categories.map((category) => (
        <li key={category.id}>
          <span onClick={() => onSelect(category.id)} style={{ cursor: 'pointer', color: 'blue' }}>
            {category.name}
          </span>
          {category.image && <img src={`data:image/jpeg;base64,${category.image}`} alt={category.name} style={{ width: '100px' }} />}
          <button onClick={() => onDelete(category.id)}>削除</button>
          {selectedCategoryId === category.id && <div>{/* ItemForm をここに追加 */}</div>}
        </li>
      ))
    ) : (
      <li>カテゴリーがありません</li>
    )}
  </ul>
);

export default CategoryList;
