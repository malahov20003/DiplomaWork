import React from 'react';
import { createRoot } from 'react-dom/client';

function App() {
    const goTo = (role) => {
        window.location.href = `/role/${role}`;
    };

    return (
        <div>
            <h1>Выбор роли</h1>
            <div className="role-buttons">
                <button onClick={() => goTo("admin")}>Администратор</button>
                <button onClick={() => goTo("manager1")}>Руководитель 1</button>
                <button onClick={() => goTo("manager2")}>Руководитель 2</button>
                <button onClick={() => goTo("employee1")}>Сотрудник 1</button>
                <button onClick={() => goTo("employee2")}>Сотрудник 2</button>
                <button onClick={() => goTo("employee3")}>Сотрудник 3</button>
                <button onClick={() => goTo("employee4")}>Сотрудник 4</button>
                <button onClick={() => goTo("employee5")}>Сотрудник 5</button>
                <button onClick={() => goTo("support")}>Поддержка</button>
            </div>
        </div>
    );
}

const root = createRoot(document.getElementById("root"));
root.render(<App />);
