import DaySelector from '@/components/DaySelector';
import { dataService } from '@/services/data';
import { Anotacion, Grupo } from '@/types';
import { format } from 'date-fns';
import React, { useEffect, useState } from 'react';
import { ActivityIndicator, Alert, FlatList, KeyboardAvoidingView, Modal, Platform, StyleSheet, Text, TextInput, TouchableOpacity, View } from 'react-native';

export default function AnotacionesScreen() {
    const [selectedDate, setSelectedDate] = useState(new Date());
    const [grupos, setGrupos] = useState<Grupo[]>([]);
    const [anotacionesMap, setAnotacionesMap] = useState<Record<number, Anotacion>>({});
    const [loading, setLoading] = useState(false);

    // Modal State
    const [modalVisible, setModalVisible] = useState(false);
    const [editingGrupo, setEditingGrupo] = useState<Grupo | null>(null);
    const [currentNoteText, setCurrentNoteText] = useState('');
    const [saving, setSaving] = useState(false);

    useEffect(() => {
        loadGrupos();
    }, []);

    useEffect(() => {
        if (grupos.length > 0) {
            loadAnotacionesForDate(selectedDate);
        }
    }, [selectedDate, grupos]);

    const loadGrupos = async () => {
        try {
            setLoading(true);
            const data = await dataService.getGrupos();
            setGrupos(data);
        } catch (error) {
            console.error('Failed to load groups', error);
        } finally {
            setLoading(false);
        }
    };

    const loadAnotacionesForDate = async (date: Date) => {
        try {
            setLoading(true);
            const dateStr = format(date, 'yyyy-MM-dd');
            const newMap: Record<number, Anotacion> = {};

            // Fetch annotations for each group for this date
            // This might be slow if many groups. 
            // Ideally backend should return "My Annotations for Date X"
            await Promise.all(grupos.map(async (grupo) => {
                try {
                    const notes = await dataService.getAnotacionesByGrupoAndDate(grupo.id, dateStr);
                    if (notes.length > 0) {
                        newMap[grupo.id] = notes[0]; // Assuming one per day per group
                    }
                } catch (e) {
                    // ignore error for specific group
                }
            }));

            setAnotacionesMap(newMap);
        } catch (e) {
            console.error('Error loading annotations', e);
        } finally {
            setLoading(false);
        }
    };

    const handleOpenNote = (grupo: Grupo) => {
        setEditingGrupo(grupo);
        const existing = anotacionesMap[grupo.id];
        setCurrentNoteText(existing ? existing.texto : '');
        setModalVisible(true);
    };

    const handleSaveNote = async () => {
        if (!editingGrupo) return;

        try {
            setSaving(true);
            const dateStr = format(selectedDate, 'yyyy-MM-dd');
            const existing = anotacionesMap[editingGrupo.id];

            if (existing) {
                if (currentNoteText.trim() === '') {
                    // Delete if empty? Or just update to empty. User requirements not specific.
                    // Let's delete for clean up if empty
                    await dataService.deleteAnotacion(editingGrupo.id, existing.id);
                } else {
                    await dataService.updateAnotacion(editingGrupo.id, existing.id, dateStr, currentNoteText);
                }
            } else {
                if (currentNoteText.trim() !== '') {
                    await dataService.createAnotacion(editingGrupo.id, dateStr, currentNoteText);
                }
            }

            setModalVisible(false);
            loadAnotacionesForDate(selectedDate); // Reload to refresh IDs etc
        } catch (error) {
            Alert.alert('Error', 'No se pudo guardar la anotación');
        } finally {
            setSaving(false);
        }
    };

    const renderItem = ({ item }: { item: Grupo }) => {
        const note = anotacionesMap[item.id];
        return (
            <TouchableOpacity style={styles.card} onPress={() => handleOpenNote(item)}>
                <View style={styles.cardHeader}>
                    <Text style={styles.groupName}>{item.nombre}</Text>
                </View>
                <Text style={styles.notePreview} numberOfLines={2}>
                    {note ? note.texto : 'Sin anotaciones...'}
                </Text>
            </TouchableOpacity>
        );
    };

    return (
        <View style={styles.container}>
            <DaySelector selectedDate={selectedDate} onSelectDate={setSelectedDate} />

            {loading && grupos.length === 0 ? (
                <ActivityIndicator style={{ marginTop: 20 }} size="large" />
            ) : (
                <FlatList
                    data={grupos}
                    renderItem={renderItem}
                    keyExtractor={(item) => item.id.toString()}
                    contentContainerStyle={styles.listContent}
                    refreshing={loading}
                    onRefresh={() => loadAnotacionesForDate(selectedDate)}
                />
            )}

            {/* Edit Modal */}
            <Modal
                animationType="slide"
                transparent={true}
                visible={modalVisible}
                onRequestClose={() => setModalVisible(false)}
            >
                <KeyboardAvoidingView
                    behavior={Platform.OS === "ios" ? "padding" : "height"}
                    style={styles.modalOverlay}
                >
                    <View style={styles.modalContent}>
                        <Text style={styles.modalTitle}>
                            {editingGrupo?.nombre} - {format(selectedDate, 'dd/MM')}
                        </Text>

                        <TextInput
                            style={styles.textInput}
                            multiline
                            textAlignVertical="top"
                            placeholder="Escribe la anotación aquí..."
                            value={currentNoteText}
                            onChangeText={setCurrentNoteText}
                        />

                        <View style={styles.modalButtons}>
                            <TouchableOpacity
                                style={[styles.modalButton, styles.cancelButton]}
                                onPress={() => setModalVisible(false)}
                            >
                                <Text style={styles.cancelButtonText}>Cancelar</Text>
                            </TouchableOpacity>

                            <TouchableOpacity
                                style={[styles.modalButton, styles.saveButton]}
                                onPress={handleSaveNote}
                                disabled={saving}
                            >
                                {saving ? <ActivityIndicator color="#fff" /> : <Text style={styles.saveButtonText}>Guardar</Text>}
                            </TouchableOpacity>
                        </View>
                    </View>
                </KeyboardAvoidingView>
            </Modal>

        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
    },
    listContent: {
        padding: 15,
    },
    card: {
        backgroundColor: '#fff',
        borderRadius: 12,
        padding: 15,
        marginBottom: 10,
        elevation: 2,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 4,
        borderWidth: 1,
        borderColor: '#f0f0f0',
    },
    cardHeader: {
        marginBottom: 5,
    },
    groupName: {
        fontSize: 18,
        fontWeight: 'bold',
        color: '#333',
    },
    notePreview: {
        fontSize: 14,
        color: '#666',
        fontStyle: 'italic',
    },
    modalOverlay: {
        flex: 1,
        backgroundColor: 'rgba(0,0,0,0.5)',
        justifyContent: 'center',
        padding: 20,
    },
    modalContent: {
        backgroundColor: '#fff',
        borderRadius: 20,
        padding: 20,
        minHeight: 300,
    },
    modalTitle: {
        fontSize: 20,
        fontWeight: 'bold',
        marginBottom: 15,
        textAlign: 'center',
    },
    textInput: {
        flex: 1,
        backgroundColor: '#f9f9f9',
        borderRadius: 10,
        padding: 15,
        marginBottom: 20,
        fontSize: 16,
        borderWidth: 1,
        borderColor: '#e0e0e0',
    },
    modalButtons: {
        flexDirection: 'row',
        justifyContent: 'space-between',
    },
    modalButton: {
        flex: 1,
        padding: 15,
        borderRadius: 10,
        alignItems: 'center',
        marginHorizontal: 5,
    },
    cancelButton: {
        backgroundColor: '#e0e0e0',
    },
    saveButton: {
        backgroundColor: '#007AFF',
    },
    cancelButtonText: {
        color: '#333',
        fontWeight: 'bold',
    },
    saveButtonText: {
        color: '#fff',
        fontWeight: 'bold',
    },
});
