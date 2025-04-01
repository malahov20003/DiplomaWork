import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import HomePage from './components/HomePage';
import AdminLayout from './components/AdminLayout';
import UserManagement from './components/UserManagement';
import AdminProjectManagement from './components/AdminProjectManagement';
import ManagerLayout from './components/ManagerLayout';
import ProjectManagement from './components/ProjectManagement';
import ReportsPage from './components/ReportsPage';

function App() {
    return (
        <Routes>
            <Route path="/" element={<HomePage />} />

            <Route path="/admin" element={<AdminLayout />}>
                <Route path="users" element={<UserManagement />} />
                <Route path="projects" element={<AdminProjectManagement />} />
                <Route path="support" element={<SupportPage />} />
            </Route>

            <Route path="/manager" element={<ManagerLayout />}>
                <Route path="projects" element={<ProjectManagement />} />
                <Route path="reports" element={<ReportsPage />} />
            </Route>
        </Routes>
    );
}

export default App;