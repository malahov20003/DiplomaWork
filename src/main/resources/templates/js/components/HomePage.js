import React from 'react';
import { useNavigate } from 'react-router-dom';

export default function HomePage() {
    const navigate = useNavigate();

    return (
        <div style={{ padding: 30 }}>
            <h2>Выберите роль для входа:</h2>
            <div style={{ display: 'flex', flexWrap: 'wrap', gap: '10px' }}>
                <button onClick={() => navigate('/admin/users')}>Администратор</button>
                <button onClick={() => navigate('/manager/projects')}>Руководитель 1</button>
                <button onClick={() => navigate('/manager/projects')}>Руководитель 2</button>
                <button onClick={() => navigate('/manager/projects')}>Сотрудник 1</button>
                <button onClick={() => navigate('/manager/projects')}>Сотрудник 2</button>
                <button onClick={() => navigate('/manager/projects')}>Сотрудник 3</button>
                <button onClick={() => navigate('/manager/projects')}>Сотрудник 4</button>
                <button onClick={() => navigate('/manager/projects')}>Сотрудник 5</button>
            </div>
        </div>
    );
}