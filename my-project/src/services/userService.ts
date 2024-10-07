import axios from 'axios';
import { User } from '../models/User';

const API_URL = '/api'; // 使用代理路徑

export const getAllUsers = async (): Promise<User[]> => {
    try {

        const response = await axios.get(`${API_URL}/users`); // 這裡使用代理路徑

        return response.data;
    } catch (error) {
        console.error('Error fetching users:', error);
        throw error;
    }
};

export const createUser = async (user: User): Promise<User> => {
    try {

        const response = await axios.post(`${API_URL}/user`, user);

        return response.data;
    } catch (error) {
        console.error('Error creating user:', error);
        throw error;
    }
};

export const updateUser = async (id: number, user: User): Promise<User> => {
    try {
        const response = await axios.put(`${API_URL}/user/${id}`, user);
        return response.data;
    } catch (error) {
        console.error('Error updating user:', error);
        throw error;
    }
};

export const deleteUser = async (id: number): Promise<void> => {
    try {
        await axios.delete(`${API_URL}/user/${id}`);
    } catch (error) {
        console.error('Error deleting user:', error);
        throw error;
    }
};

