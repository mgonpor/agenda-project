import React from 'react';
import { ScrollView, StyleSheet, Text, View } from 'react-native';
import { Clase, Grupo } from '../types';

interface WeekCalendarProps {
    clases: Clase[];
    grupos: Grupo[];
    tramos: number; // e.g., 6, 7, or 12 periods
}

const WeekDays = ['Lun', 'Mar', 'Mié', 'Jue', 'Vie'];
const DayMap: { [key: string]: number } = {
    'MONDAY': 0,
    'TUESDAY': 1,
    'WEDNESDAY': 2,
    'THURSDAY': 3,
    'FRIDAY': 4,
};

export default function WeekCalendar({ clases, grupos, tramos }: WeekCalendarProps) {
    // Helper to find class at (dayIndex, tramoIndex)
    const getClassAt = (dayIndex: number, tramo: number) => {
        return clases.find(c => {
            const cDayIndex = DayMap[c.diaSemana]; // Make sure API matches this or map accordingly
            // Assuming tramo is 1-based index from API
            return cDayIndex === dayIndex && c.tramo === tramo;
        });
    };

    const getGrupoName = (idGrupo: number) => {
        const g = grupos.find(group => group.id === idGrupo);
        return g ? g.nombre : 'Unknown';
    };

    return (
        <View style={styles.container}>
            {/* Header Row (Days) */}
            <View style={styles.headerRow}>
                <View style={styles.timeColumnHeader} />
                {WeekDays.map((day, index) => (
                    <View key={index} style={styles.headerCell}>
                        <Text style={styles.headerText}>{day}</Text>
                    </View>
                ))}
            </View>

            <ScrollView>
                {/* Grid Rows (Tramos) */}
                {Array.from({ length: tramos }).map((_, i) => {
                    const tramoNumber = i + 1;
                    return (
                        <View key={tramoNumber} style={styles.row}>
                            {/* Time Column */}
                            <View style={styles.timeColumn}>
                                <Text style={styles.timeText}>{tramoNumber}º</Text>
                            </View>

                            {/* Day Columns */}
                            {WeekDays.map((_, dayIndex) => {
                                const clase = getClassAt(dayIndex, tramoNumber);
                                return (
                                    <View key={dayIndex} style={styles.cell}>
                                        {clase ? (
                                            <View style={styles.claseContainer}>
                                                <Text style={styles.claseGrupo}>{getGrupoName(clase.idGrupo)}</Text>
                                                <Text style={styles.claseAula}>{clase.aula}</Text>
                                            </View>
                                        ) : null}
                                    </View>
                                );
                            })}
                        </View>
                    );
                })}
            </ScrollView>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
    },
    headerRow: {
        flexDirection: 'row',
        borderBottomWidth: 1,
        borderBottomColor: '#ccc',
        backgroundColor: '#f9f9f9',
        paddingVertical: 5,
    },
    timeColumnHeader: {
        width: 40,
        borderRightWidth: 1,
        borderRightColor: '#ccc',
    },
    headerCell: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
    headerText: {
        fontWeight: 'bold',
        color: '#333',
    },
    row: {
        flexDirection: 'row',
        borderBottomWidth: 1,
        borderBottomColor: '#eee',
        minHeight: 60,
    },
    timeColumn: {
        width: 40,
        alignItems: 'center',
        justifyContent: 'center',
        backgroundColor: '#f0f0f0',
        borderRightWidth: 1,
        borderRightColor: '#ccc',
    },
    timeText: {
        fontWeight: 'bold',
        color: '#666',
    },
    cell: {
        flex: 1,
        borderRightWidth: 1,
        borderRightColor: '#eee',
        padding: 2,
    },
    claseContainer: {
        flex: 1,
        backgroundColor: '#e3f2fd',
        borderRadius: 4,
        padding: 2,
        justifyContent: 'center',
        alignItems: 'center',
    },
    claseGrupo: {
        fontSize: 12,
        fontWeight: 'bold',
        textAlign: 'center',
        color: '#1565c0',
    },
    claseAula: {
        fontSize: 10,
        color: '#555',
        textAlign: 'center',
    },
});
