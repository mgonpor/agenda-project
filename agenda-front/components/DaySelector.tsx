import { addDays, format, isSameDay, subDays } from 'date-fns';
import { es } from 'date-fns/locale';
import React, { useEffect, useRef } from 'react';
import { ScrollView, StyleSheet, Text, TouchableOpacity, View } from 'react-native';

interface DaySelectorProps {
    selectedDate: Date;
    onSelectDate: (date: Date) => void;
}

export default function DaySelector({ selectedDate, onSelectDate }: DaySelectorProps) {
    const scrollViewRef = useRef<ScrollView>(null);

    // Generate range of dates (e.g., -15 to +15 days)
    const days = Array.from({ length: 30 }, (_, i) => {
        return addDays(subDays(new Date(), 15), i);
    });

    useEffect(() => {
        // Scroll to center initially (approximate)
        // 60 is approx item width + margin
        setTimeout(() => {
            scrollViewRef.current?.scrollTo({ x: 15 * 60, animated: true });
        }, 100);
    }, []);

    return (
        <View style={styles.container}>
            <ScrollView
                ref={scrollViewRef}
                horizontal
                showsHorizontalScrollIndicator={false}
                contentContainerStyle={styles.scrollContent}
            >
                {days.map((date, index) => {
                    const isSelected = isSameDay(date, selectedDate);
                    return (
                        <TouchableOpacity
                            key={index}
                            style={[styles.dayItem, isSelected && styles.selectedItem]}
                            onPress={() => onSelectDate(date)}
                        >
                            <Text style={[styles.dayName, isSelected && styles.selectedText]}>
                                {format(date, 'EEE', { locale: es })}
                            </Text>
                            <Text style={[styles.dayNumber, isSelected && styles.selectedText]}>
                                {format(date, 'd')}
                            </Text>
                        </TouchableOpacity>
                    );
                })}
            </ScrollView>
            {/* 
        TODO: Add Calendar Icon Picker here if a library is available.
        For now, just the horizontal strip.
      */}
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        height: 80,
        backgroundColor: '#fff',
        borderBottomWidth: 1,
        borderBottomColor: '#eee',
        justifyContent: 'center',
    },
    scrollContent: {
        alignItems: 'center',
        paddingHorizontal: 10,
    },
    dayItem: {
        width: 60,
        height: 60,
        justifyContent: 'center',
        alignItems: 'center',
        marginHorizontal: 5,
        borderRadius: 12, // Rounded squares
        backgroundColor: '#f5f5f5',
    },
    selectedItem: {
        backgroundColor: '#007AFF',
    },
    dayName: {
        fontSize: 12,
        color: '#666',
        textTransform: 'uppercase',
    },
    dayNumber: {
        fontSize: 20,
        fontWeight: 'bold',
        color: '#333',
    },
    selectedText: {
        color: '#fff',
    },
});
